package net.brdloush.thymeleaf.perfissue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brdloush on 29.1.2015.
 */
public class FeatureGroup {

    private List<Feature> features = new LinkedList<Feature>();

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
