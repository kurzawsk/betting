package com.kk.betting.test.util;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-06-10.
 */
public class DeploymentGenerator {

    private static final String TEST_PERSISTENCE_SOURCE = "test-persistence.xml";
    private static final String MAPPED_PERSISTENCE_SOURCE = "META-INF/persistence.xml";
    private static final String CDI_ACTIVATOR_RESOURCE = "beans.xml";
    private static final String LOAD_CLASS_PKG_PREFIX = "com.kk.betting";


    public static Archive createDeployment() throws IOException {

        File[] libs = Maven.resolver().loadPomFromFile("D:\\projects_intellij\\betting\\betting-services\\pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();


        ZipImporter importer = ShrinkWrap
                .create(ZipImporter.class);

        for (File file : libs) {
            importer = importer.importFrom(file);
        }

        JavaArchive archive = importer
                .as(JavaArchive.class).addAsResource(new File("D:\\projects_intellij\\betting\\betting-services\\src\\integration-test\\resources\\META-INF\\" + TEST_PERSISTENCE_SOURCE), MAPPED_PERSISTENCE_SOURCE)
                .addAsManifestResource(EmptyAsset.INSTANCE, CDI_ACTIVATOR_RESOURCE);


//        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.war")
//                .addAsResource(new File("D:\\projects_intellij\\betting\\betting-services\\src\\integration-test\\resources\\META-INF\\" + TEST_PERSISTENCE_SOURCE), MAPPED_PERSISTENCE_SOURCE)
//                .addAsManifestResource(EmptyAsset.INSTANCE, CDI_ACTIVATOR_RESOURCE); //.addAsLibraries(files);
        for (Class clazz : getTestClasses()) { //.stream().forEach(clazz -> archive.addClass(clazz));
            archive.addClasses(clazz);
        }
//        for (JavaArchive lib : libs) {
//            archive = archive.imp
//        }
//a.addAsDirectories()

//        JavaArchive archive = ShrinkWrap.create(JavaArchive.class).
//                addClass(SimpleMatchBettingScenariosTest.class).
//                //TODO change/fix path to relative
//                addAsResource(new File("D:\\projects_intellij\\betting\\betting-services\\src\\integration-test\\resources\\META-INF\\"+TEST_PERSISTENCE_SOURCE), MAPPED_PERSISTENCE_SOURCE).
//                addAsManifestResource(EmptyAsset.INSTANCE, CDI_ACTIVATOR_RESOURCE);
//        getTestClasses().stream().forEach(clazz -> archive.addClass(clazz));
        return archive;

    }

    private static List<Class<?>> getTestClasses() throws IOException {
        List<Class<?>> classes = Lists.newLinkedList();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        classes.addAll(ClassPath.from(loader).getTopLevelClasses().stream()
                .filter(info -> info.getName().startsWith(LOAD_CLASS_PKG_PREFIX))
                .map((Function<ClassPath.ClassInfo, Class<?>>) ClassPath.ClassInfo::load).collect(Collectors.toList()));

        return classes;
    }
}
