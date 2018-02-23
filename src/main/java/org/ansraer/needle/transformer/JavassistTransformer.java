package org.ansraer.needle.transformer;

import javassist.CtClass;

public abstract class JavassistTransformer {

    public final String PACKAGE_NAME;
    public final String CLASS_NAME;

    public JavassistTransformer(String packageName, String className) {
        this.PACKAGE_NAME = packageName;
        this.CLASS_NAME = className;
    }

    public abstract CtClass modifyClass(CtClass cc);
}
