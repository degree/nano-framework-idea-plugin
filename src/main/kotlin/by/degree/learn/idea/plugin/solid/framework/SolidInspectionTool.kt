package by.degree.learn.idea.plugin.solid.framework

import com.intellij.codeInsight.intention.AddAnnotationFix
import com.intellij.codeInspection.*
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField

class SolidInspectionTool : AbstractBaseJavaLocalInspectionTool() {
    override fun checkField(
        field: PsiField,
        manager: InspectionManager,
        isOnTheFly: Boolean
    ): Array<ProblemDescriptor>? {

        val problemsHolder = ProblemsHolder(manager, field.containingFile, isOnTheFly)

        check(field, ANT_INJECT, problemsHolder)
        check(field, ANT_INJECT_PROPERTY, problemsHolder)

        return problemsHolder.resultsArray
    }

    private fun check(
        field: PsiField,
        annotationName: String,
        problemsHolder: ProblemsHolder
    ) {
        val annotation = field.getAnnotation(annotationName)
        if (annotation != null && !FrameworkHelper.isComponent(field.containingClass)) {
            problemsHolder.registerProblem(
                annotation,
                "@Component annotation missing in class with injections",
                ProblemHighlightType.WARNING,
                AddAnnotationFix(ANT_COMPONENT, field.containingClass!!)
            )
        }
    }

    override fun checkClass(
        aClass: PsiClass,
        manager: InspectionManager,
        isOnTheFly: Boolean
    ): Array<ProblemDescriptor>? {
        if (!FrameworkHelper.isComponent(aClass) && aClass.interfaces.any(FrameworkHelper::isPartOfFramework)) {
            val problemsHolder = ProblemsHolder(manager, aClass.containingFile, isOnTheFly)
            problemsHolder.registerProblem(
                aClass.identifyingElement!!,
                "@Component annotation missing for class implementing framework interface",
                ProblemHighlightType.WARNING,
                AddAnnotationFix(ANT_COMPONENT, aClass)
            )
            return problemsHolder.resultsArray
        }
        return ProblemDescriptor.EMPTY_ARRAY
    }
}
