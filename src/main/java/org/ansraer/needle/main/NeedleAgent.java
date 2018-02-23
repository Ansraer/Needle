package org.ansraer.needle.main;

import org.ansraer.needle.transformer.CardCrawlGameTransformer;
import org.ansraer.needle.transformer.LeaderboardPosterTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Properties;

public class NeedleAgent {

    public static String VERSION;
    public static String ARTIFACTID;

    public static void premain(String agentArgs, Instrumentation inst) {
        //Loading Needle infromation
        final Properties properties = new Properties();
        try {
            properties.load(NeedleAgent.class.getClassLoader().getResourceAsStream("version.prop"));
            VERSION = properties.getProperty("version");
            ARTIFACTID = properties.getProperty("artifactId");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Starting Needle");
        System.out.println(ARTIFACTID+" version:"+VERSION);

        /**The following transformers are where the bytecode injection happens
         * TO-DO: optimize performance
         */
        TransformerManager.registerTransformer(new CardCrawlGameTransformer("com.megacrit.cardcrawl.core","CardCrawlGame"));
        TransformerManager.registerTransformer(new LeaderboardPosterTransformer("com.megacrit.cardcrawl.metrics","LeaderboardPoster"));

        inst.addTransformer(new TransformerManager());

    }

}
