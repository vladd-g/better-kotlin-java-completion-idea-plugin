package codes.vg.betterkotlinjavacompletion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.lang.java.JavaLanguage
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiJavaFile
import com.intellij.util.ProcessingContext
import org.jetbrains.kotlin.asJava.elements.KtLightMethod
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.psiUtil.containingClass

internal class KotlinDataClassCompletionContributor : CompletionContributor() {

    private val completionProvider = object : CompletionProvider<CompletionParameters>() {
        override fun addCompletions(
            parameters: CompletionParameters,
            context: ProcessingContext,
            result: CompletionResultSet,
        ) {
            val position = parameters.position
            if (position.containingFile !is PsiJavaFile)
                return
            result.runRemainingContributors(parameters) { completion ->
                val lookupElement = completion.lookupElement
                if (!shouldFilterOut(lookupElement)) {
                    result.addElement(lookupElement)
                }
            }
        }
    }

    private val componentNRegex = Regex("^component[1-9]\\d*$")

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(JavaLanguage.INSTANCE),
            completionProvider,
        )
    }

    private fun shouldFilterOut(lookupElement: LookupElement): Boolean {
        val psiElement = lookupElement.psiElement as? KtLightMethod
            ?: return false
        val ktOrigin = psiElement.kotlinOrigin
            ?: return false
        val isParamInDataClass = ktOrigin.containingClass()?.isData() == true && ktOrigin is KtParameter // Generated `componentN()` in data classes are treated by Kotlin compiler as value parameters, not functions
        if (!isParamInDataClass)
            return false
        return componentNRegex.matches(psiElement.name)
    }
}