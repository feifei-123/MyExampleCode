package com.feifei.second.hostconfig


public class HostConfig {
    static def void createHostConfig(variant,config){

def content = """
package com.sogou.teemo.test_use_gradle_plugin;
public class TheHostConfig{
    public static final String ip = "${config.param1}";
    public static final String port = "5050"; 

}

"""

        File outputDir = variant.getVariantData().getScope().getBuildConfigSourceOutputDir()
        println "feifei createHostConfig outputDir:"+outputDir.getAbsolutePath()
        def javaFile = new File(outputDir, "TheHostConfig.java")
        javaFile.write(content,'UTF-8')

    }
}