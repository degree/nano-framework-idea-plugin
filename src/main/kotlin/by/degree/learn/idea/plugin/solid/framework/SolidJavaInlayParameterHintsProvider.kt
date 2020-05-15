package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.hints.InlayInfo
import com.intellij.codeInsight.hints.InlayParameterHintsProvider
import com.intellij.psi.*

class SolidJavaInlayParameterHintsProvider : InlayParameterHintsProvider {
    private val blackList = emptySet<String>()

    override fun getDefaultBlackList(): Set<String> = blackList

    override fun getParameterHints(element: PsiElement): List<InlayInfo> {
        if (element is PsiNameIdentifierOwner) {
            when (element) {
                is PsiMethod, is PsiClass -> return listOf(InlayInfo("^" + element.name, element.textRange.endOffset, isShowOnlyIfExistedBefore = false, isFilterByBlacklist = false, relatesToPrecedingText = true))
                is PsiField -> return listOf(InlayInfo("field", element.textRange.endOffset,
                    isShowOnlyIfExistedBefore = false,
                    isFilterByBlacklist = false,
                    relatesToPrecedingText = true
                ))
            }
        }
        return emptyList()
    }

    override fun getInlayPresentation(inlayText: String) = inlayText
}
