package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiQualifiedNamedElement
import com.intellij.psi.search.GlobalSearchScope

object FrameworkHelper {
    fun isComponent(element: PsiClass?): Boolean {
        if (element == null) {
            return false
        }
        val project = element.project
        val javaPsiFacade = JavaPsiFacade.getInstance(project)
        val scope = GlobalSearchScope.allScope(project)

        return isComponentRecursive(element, javaPsiFacade, scope)
    }

    private fun isComponentRecursive(
        element: PsiClass,
        javaPsiFacade: JavaPsiFacade,
        scope: GlobalSearchScope
    ): Boolean {
        return hasComponentAnnotation(element)
                || listAnnotationElements(element)
            .mapNotNull { it.qualifiedName }
            .filter { !it.startsWith("java.lang.annotation") }
            .mapNotNull { javaPsiFacade.findClass(it, scope) }
            .any { isComponentRecursive(it, javaPsiFacade, scope) }
    }

    private fun listAnnotationElements(element: PsiClass): List<PsiAnnotation> =
        element.modifierList?.children?.filterIsInstance<PsiAnnotation>() ?: emptyList()

    private fun hasComponentAnnotation(element: PsiClass) =
        AnnotationUtil.isAnnotated(element, ANT_COMPONENT, 0)

    fun isPartOfFramework(element: PsiClass): Boolean {
        return inFrameworkPackage(element) || element.interfaces.any(this::inFrameworkPackage)
    }

    fun inFrameworkPackage(psiClass: PsiQualifiedNamedElement) =
        psiClass.qualifiedName?.startsWith(FRAMEWORK_PACKAGE) ?: false

}
