package org.ansraer.needle.main;

import javassist.ClassPool;
import javassist.CtClass;
import org.ansraer.needle.transformer.JavassistTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;

public class TransformerManager implements ClassFileTransformer {

    public static ArrayList<JavassistTransformer> transformers = new ArrayList();


    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        byte[] byteCode = null;

        for(JavassistTransformer t : transformers){

            if ((t.PACKAGE_NAME.replace(".", "/")+"/"+t.CLASS_NAME).equals(className)){


                try {
                    ClassPool cp = ClassPool.getDefault();
                    CtClass cc = cp.get(t.PACKAGE_NAME+"."+t.CLASS_NAME);
                    cc = t.modifyClass(cc);

                    byteCode = cc.toBytecode();
                    cc.detach();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //remove transformer from list after it's job was done
                transformers.remove(t);
                break;
            }
        }

        return byteCode;
    }

    public static void registerTransformer(JavassistTransformer t){
        transformers.add(t);
    }

}