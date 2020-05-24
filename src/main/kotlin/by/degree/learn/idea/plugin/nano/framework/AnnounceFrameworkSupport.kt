package by.degree.learn.idea.plugin.nano.framework

import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.ui.awt.RelativePoint

class AnnounceFrameworkSupport : StartupActivity {
    override fun runActivity(project: Project) {
        DumbService.getInstance(project).runWhenSmart {
            val psi = JavaPsiFacade.getInstance(project)
            val scope = GlobalSearchScope.allScope(project)

            if (psi.findClass(FRAMEWORK_CLASS, scope) != null) {
                val statusBar = WindowManager.getInstance().getStatusBar(project)
                JBPopupFactory.getInstance()
                    .createHtmlTextBalloonBuilder("<string>SOLID</strong> Framework found", MessageType.INFO, null)
                    .setHideOnCloseClick(true)
                    .createBalloon()
                    .show(RelativePoint.getSouthEastOf(statusBar.component), Balloon.Position.above)

            }
        }
    }
}
