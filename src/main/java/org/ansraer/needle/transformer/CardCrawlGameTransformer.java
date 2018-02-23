package org.ansraer.needle.transformer;

import javassist.*;
import javassist.bytecode.FieldInfo;
import org.ansraer.needle.main.NeedleAgent;

import javax.sound.midi.SysexMessage;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class CardCrawlGameTransformer extends JavassistTransformer {

    public CardCrawlGameTransformer(String packageName, String className) {
        super(packageName, className);
    }

    public CtClass modifyClass(CtClass cc) {

        System.out.println("Adding Needle Version to version number");

        //adding needle to version info
        try {
            CtMethod create = cc.getDeclaredMethod("create");
            create.insertBefore("VERSION_NUM += \" ; "+NeedleAgent.ARTIFACTID.toUpperCase() + ": " + NeedleAgent.VERSION +"\";");
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        return cc;
    }
}