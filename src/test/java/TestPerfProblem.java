import junit.framework.TestCase;
import net.brdloush.thymeleaf.perfissue.Feature;
import net.brdloush.thymeleaf.perfissue.Model;
import net.brdloush.thymeleaf.perfissue.Phone;
import net.brdloush.thymeleaf.perfissue.FeatureGroup;
import org.springframework.util.StopWatch;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TestPerfProblem extends TestCase {


    public void testPerfIssue() {

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


        // initialize thymeleaf 2.1.4 + Spring 3.2 Template Engine
        TemplateEngine templateEngine = new SpringTemplateEngine();
        StandardDialect standardDialect = (StandardDialect) templateEngine.getDialects().iterator().next();

        StandardExpressionParser expParser = (StandardExpressionParser) standardDialect.getExpressionParser();
        System.out.println(expParser.getClass().getName());
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML5");
        resolver.setSuffix(".html");
        templateEngine.setTemplateResolver(resolver);
        IContext context = new Context();

        // feed dummy model to context
        context.getVariables().put("model", model);

        // perform tests
        StopWatch sw = new StopWatch();

        sw.start();
        int runTimes = 10;
        for (int i=0;i<runTimes;i++) {
            String processed = templateEngine.process("thymeleaf-template", context);
        }
        sw.stop();

        // printout results
        System.out.println("Total time spent processing "+ sw.getTotalTimeMillis()+" ms");
        System.out.println("Processing of single page takes "+ (sw.getTotalTimeMillis()/runTimes)+" ms");
    }
}
