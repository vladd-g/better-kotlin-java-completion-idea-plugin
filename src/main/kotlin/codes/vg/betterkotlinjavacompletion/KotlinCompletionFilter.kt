package codes.vg.betterkotlinjavacompletion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.psi.PsiElement

internal interface KotlinCompletionFilter {
    fun shouldFilterOut(psiElement: PsiElement?, parameters: CompletionParameters): Boolean
}
