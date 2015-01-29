package net.brdloush.thymeleaf.perfissue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brdloush on 29.1.2015.
 */
public class Phone {

    private List<FeatureGroup> featureGroups = new LinkedList<FeatureGroup>();

    public List<FeatureGroup> getFeatureGroups() {
        return featureGroups;
    }

    public void setFeatureGroups(List<FeatureGroup> featureGroups) {
        this.featureGroups = featureGroups;
    }
}
