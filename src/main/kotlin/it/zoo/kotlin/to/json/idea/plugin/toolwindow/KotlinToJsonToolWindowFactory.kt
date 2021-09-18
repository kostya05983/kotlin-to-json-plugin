package it.zoo.kotlin.to.json.idea.plugin.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class KotlinToJsonToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val kotlinToJsonToolWindow = KotlinToJsonToolWindow()
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(kotlinToJsonToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}