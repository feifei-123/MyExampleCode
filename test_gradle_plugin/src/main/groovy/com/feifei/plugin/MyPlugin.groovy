package  com.feifei.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class MyPlugin implements Plugin<Project>{

    void apply(Project project){

//        project.task("testTask")<<{
//            System.out.println("==============")
//            System.out.println("Hello gradle plugin!!")
//            System.out.println("==============")
//        }

        project.gradle.addListener(new TimeListener())

        project.extensions.create("pluginExt",PluginExtension)
        project.pluginExt.extensions.create("nestExt",PluginNestExtension)
        project.task('customTask',type:CustomTask)
    }
}