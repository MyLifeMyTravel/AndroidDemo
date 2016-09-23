package com.yidao.pack;

import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.gradle.api.tasks.Input

@CompileStatic
class PostPackExtension {

    @Input
    boolean enable = false

    @Input
    boolean supportClean = false;

    @Input
    boolean supportEncrpyt = false

    @Input
    boolean supportMultiChannel = false

    public static PostPackExtension getConfig(Project project) {
        PostPackExtension config =
                project.getExtensions().findByType(PostPackExtension.class);
        if (config == null) {
            config = new PostPackExtension();
        }
        return config;
    }

}
