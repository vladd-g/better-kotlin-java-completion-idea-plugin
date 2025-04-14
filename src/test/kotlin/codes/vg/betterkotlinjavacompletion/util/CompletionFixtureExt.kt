package codes.vg.betterkotlinjavacompletion.util

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProcess
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiMethod
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile

internal fun CodeInsightTestFixture.completionParametersAtCaret(): CompletionParameters {
    val file = this.file
    val editor = this.editor
    val offset = editor.caretModel.offset
    return CompletionParameters(
        file.findElementAt(offset - 1)!!,
        file,
        CompletionType.BASIC,
        offset,
        1,
        editor,
        object : CompletionProcess {
            override fun isAutopopupCompletion() = false
        },
    )
}

internal fun CodeInsightTestFixture.lightClassMethod(className: String, methodName: String): PsiMethod {
    val vFile = findFileInTempDir("$className.kt")
        ?: error("$className.kt not found in temp dir")
    val psiFile = PsiManager.getInstance(project).findFile(vFile)
        ?: error("PsiFile for $className.kt not found")
    val ktFile = psiFile as? KtFile
        ?: error("PsiFile is not a KtFile")

    val desiredClass = ktFile.declarations
        .filterIsInstance<KtClass>()
        .firstOrNull { it.name == className }
        ?: error("$className class not found in KtFile")

    val lightClass = desiredClass.toLightClass()
        ?: error("No light class for $className")

    return lightClass.methods.first { it.name == methodName }
}
