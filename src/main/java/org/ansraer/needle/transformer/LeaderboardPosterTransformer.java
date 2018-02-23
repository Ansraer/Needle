package org.ansraer.needle.transformer;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.ansraer.needle.main.NeedleAgent;

import java.lang.instrument.ClassFileTransformer;

public class LeaderboardPosterTransformer extends JavassistTransformer {

    public LeaderboardPosterTransformer(String packageName, String className) {
        super(packageName, className);
    }

    public CtClass modifyClass(CtClass cc) {

        System.out.println("disabling leaderboard uploads");

        //adding needle to version info
        try {
            CtMethod sendPost = cc.getDeclaredMethod("sendPost");
            sendPost.insertBefore("System.out.println(\"blocked leaderboard upload\"); return;");
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        return cc;
    }
}