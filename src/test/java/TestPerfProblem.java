import junit.framework.TestCase;
import net.brdloush.thymeleaf.perfissue.Feature;
import net.brdloush.thymeleaf.perfissue.FeatureGroup;
import net.brdloush.thymeleaf.perfissue.Model;
import net.brdloush.thymeleaf.perfissue.Phone;

import org.springframework.util.StopWatch;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TestPerfProblem extends TestCase {


	private TemplateEngine getInitializedEngine() {
        // initialize thymeleaf 2.1.4 + Spring 3.2 Template Engine
        TemplateEngine templateEngine = new TemplateEngine();
        StandardDialect standardDialect = (StandardDialect) templateEngine.getDialects().iterator().next();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML5");
        resolver.setSuffix(".html");
        templateEngine.setTemplateResolver(resolver);

        return templateEngine;
	}
    public void testPerfIssue() {

    	TemplateEngine initializedEngine = getInitializedEngine();
    	runTest("DRY RUN", "thymeleaf-template", initializedEngine, 100);
    	runTest("MEASURING RUN", "thymeleaf-template", initializedEngine, 100);
    	
    	runTest("DRY RUN", "thymeleaf-template-noremove", initializedEngine,100);
    	runTest("MEASURING RUN", "thymeleaf-template-noremove", initializedEngine,100);
    	
    }

	private void runTest(String testName, String template, TemplateEngine templateEngine, int runTestIterations) {
		  // prepare dummy model -> just linked POJOs: 30 phones, each has 5 feature groups, each group has 10 features.
        int numPhones = 30;
        int numFeatureGroupsPerPhone = 5;
        int numFeaturesInGroup = 10;

        Model model = new Model();
        for (int i=0;i<numPhones;i++) {
            Phone phone = new Phone();
            for (int j=0;j<numFeatureGroupsPerPhone;j++) {
                FeatureGroup group = new FeatureGroup();
                for (int k=0;k<numFeaturesInGroup;k++) {
                    Feature feature = new Feature();
                    group.getFeatures().add(feature);
                }
                phone.getFeatureGroups().add(group);
            }
            model.getPhones().add(phone);
        }



        // feed dummy model to context
        IContext context = new Context();
        context.getVariables().put("model", model);

        // perform tests
        StopWatch sw = new StopWatch(testName);

        String processed = "";
        
        sw.start();
        for (int i=0;i<runTestIterations;i++) {
        	processed = templateEngine.process(template, context);
        }
        sw.stop();

        // printout results
        System.out.println("*** "+testName);
        System.out.println("Total time spent processing "+ sw.getTotalTimeMillis()+" ms");
        System.out.println("Processing of single page takes "+ (sw.getTotalTimeMillis()/runTestIterations)+" ms");
	}
}
