package codes.vg.betterkotlinjavacompletion.dataclass

import codes.vg.betterkotlinjavacompletion.KotlinCompletionFilter
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.asJava.elements.KtLightMethod
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.psiUtil.containingClass

internal class KotlinDataClassComponentNFilter: KotlinCompletionFilter {

    private val componentNRegex = Regex("^component[1-9]\\d*$")

    override fun shouldFilterOut(psiElement: PsiElement?, parameters: CompletionParameters): Boolean {
        if (psiElement !is KtLightMethod)
            return false
        val ktOrigin = psiElement.kotlinOrigin
            ?: return false
        val isParamInDataClass = ktOrigin.containingClass()?.isData() == true && ktOrigin is KtParameter // Generated `componentN()` in data classes are treated by Kotlin compiler as value parameters, not functions
        return isParamInDataClass && componentNRegex.matches(psiElement.name)
    }
}
