package codes.vg.betterkotlinjavacompletion

import codes.vg.betterkotlinjavacompletion.dataclass.KotlinDataClassComponentNFilter
import com.intellij.codeInsight.completion.*
import com.intellij.lang.java.JavaLanguage
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiJavaFile
import com.intellij.util.ProcessingContext

internal class KotlinCompletionContributor : CompletionContributor() {
    private val filters = setOf(
        KotlinDataClassComponentNFilter(),
    )

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
                val shouldShow = filters.none { it.shouldFilterOut(lookupElement.psiElement, parameters) }
                if (shouldShow) {
                    result.addElement(lookupElement)
                }
            }
        }
    }

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(JavaLanguage.INSTANCE),
            completionProvider,
        )
    }
}
