package com.dagent;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import opennlp.tools.sentdetect.SentenceDetectorME; 
import opennlp.tools.sentdetect.SentenceModel;
import com.dagent.SemanticLTM;
import java.util.concurrent.ThreadLocalRandom; // get random number


public class TextGenAgent {
	
	
	public TextGenAgent() {}
	
	public static String generateActionSentence(TextPlan t) {
		int min = 0;
		int max = t.getWishesList().size();
		int randomIndex = ThreadLocalRandom.current().nextInt(min, max);
		return t.getWishesList().get(randomIndex);
	}
	
	public static String generateDescriptionSetence(TextPlan t) {
		HashMap<String, String> plan = t.getDescription();
		String emotionStrength = plan.get("strength");
		
		
		
		return null;
	}
	
	public static void load_test_knowledge() {
		
		// Insert Knowledge
		SemanticLTM sm = SemanticLTM.getInstance();
				
		// Add known Activities - Food ball match
		
		// Hard code version
		String footballMatch = "watch football match";
		HashMap<String, String> fbm = new HashMap<String, String>();
		fbm.put("name", "arsonal vs manchester united");
		fbm.put("team_1", "Arsonal");
		fbm.put("team_2", "Manchester United");
		fbm.put("Date", "Day 1");
		sm.setKnownActivities(footballMatch, fbm);
		// Version 2
		
		
		// Add known Activities - Opera
		String opera = "watch opera";
		HashMap<String, String> opr = new HashMap<String, String>();
		opr.put("name", "phantum of the opera");
		opr.put("Date", "Day 6");
		sm.setKnownActivities(opera, opr);
		
		// Add known ACtivities
		
		// Add known Restaurant - Japanese
		String japanese_food = "japanese food";
		HashMap<String, String> jres = new HashMap<String, String>();
		jres.put("name", "Birdie");
		sm.setKnownResturant(japanese_food, jres);
		
		// Add known Restaurant - French
		String french_food = "french food";
		HashMap<String, String> fres = new HashMap<String, String>();
		fres.put("name", "L'Atelier de Joël Robuchon Hong Kong");
		sm.setKnownResturant(french_food, fres);
			
		// Add known Restaurant - Dim sum
		String dimsum_food = "dim sum food";
		HashMap<String, String> dres = new HashMap<String, String>();
		dres.put("name", "Dim Sum Library Pacific Place");
		sm.setKnownResturant(dimsum_food, dres);
		
		// Add known People - Amy
		String amyKey = "Amy";
		HashMap<String, String> amyAttr = new HashMap<String, String>();
		amyAttr.put("fav", "high");
		amyAttr.put("status", "friend");
		amyAttr.put("fav food", "japanese food");
		amyAttr.put("fav act", "watch opera");
		amyAttr.put("feat", "good appearence");
		sm.setKnownPerson(amyKey, amyAttr);
		
		// Add known People - Ann
		String annKey = "Ann";
		HashMap<String, String> annAttr = new HashMap<String, String>();
		annAttr.put("fav", "high");
		annAttr.put("status", "unmet");
		annAttr.put("fav food", null);
		annAttr.put("fav act", "watch foodball match");
		annAttr.put("feat", "kind to animal");
		sm.setKnownPerson(annKey, annAttr);
		
		// Add known People - Ashely
		String ashleyKey = "Ashely";
		HashMap<String, String> ashleyAttr = new HashMap<String, String>();
		ashleyAttr.put("fav", "high");
		ashleyAttr.put("status", "unmet");
		ashleyAttr.put("feat", "gossiper");
		sm.setKnownPerson(ashleyKey, ashleyAttr);
		
		// Add self People
		String selfKey = "self";
		HashMap<String, String> selfAttr = new HashMap<String, String>();
		selfAttr.put("fav food", "french food");
		selfAttr.put("fav act", "watch foodball match");
		sm.setKnownPerson(selfKey, selfAttr);
	}
	
	public static final void main(String[] args) {
		
		load_test_knowledge();
		
		SemanticLTM sm = SemanticLTM.getInstance();
		
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("kession-textgen");
        	
            // Working memory
            AppraisalResult ar1 = new AppraisalResult();
            TextPlan tp1 = new TextPlan();
            
            ar1.setObservedFeautre("be: kind, animal");
            ar1.setTarget("Amy");
            ar1.setFacing("Amy");
            ar1.updateFieldFromAppraisalFrame("inv", 0.8); // involvement
            ar1.updateFieldFromAppraisalFrame("dis", 0.0); // dissimilarity
            ar1.updateFieldFromAppraisalFrame("ethicPos", 0.8);
            ar1.updateFieldFromAppraisalFrame("sim", 0.8);
            ar1.updateFieldFromAppraisalFrame("action", 1.0); // Let say 1 stand for approach
            
            long startTime = System.nanoTime();
            kSession.insert(sm);
            kSession.insert(ar1);
            kSession.insert(tp1);
            kSession.fireAllRules();
            long endTime = System.nanoTime();
            
            System.out.println("time taken to run the first test case: " + (double)(endTime - startTime)/1000000000 + " seconds." );
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
	
	public static class AppraisalResult {
		
		private String target;
		
		private String facing;
		
		private String observed;
		
		private HashMap<String, Double> appraisalDomain;
		
		public AppraisalResult() {
			appraisalDomain = new HashMap<String, Double>();
		}
		
		public String getTarget() { return target; }
		
		public void setTarget(String s) { target = s; }
		
		public String getFacing() { return facing; }
		
		public void setFacing(String s) {  facing = s; }
		
		public String getObservedFeature() { return observed; }
		
		public void setObservedFeautre(String of) { observed = of; }
		
		public Double getAppraisalDomain(String field) {
			return appraisalDomain.get(field);
		}
		
		public void updateFieldFromAppraisalFrame(String field, Double value) {
			if (appraisalDomain.containsKey(field))
				appraisalDomain.replace(field, value);
			else
				appraisalDomain.put(field, value);
		}
		
	}
	
	public static class TextPlan {
		
		private HashMap<String, String> description;
		
		private ArrayList<String> wishesList;
		
		private ArrayList<String> remains;

		public TextPlan() {
			
			description = new HashMap<String, String>();
					
			wishesList = new ArrayList<String>();
		}
		
		public HashMap<String, String> getDescription() { return description; }
		
		public void setDescription(String part, String content) {
			if (description.containsKey(part)) {
				description.replace(part, content);
			}
			else {
				description.put(part, content);
			}
		}
		
		public ArrayList<String> getWishesList() { return wishesList; }
		
		public void addWish(String wish) { wishesList.add(wish); }
		
		public ArrayList<String> getRemains() { return remains; }
		
		public void setRemains(String s) {
			remains.add(s); }
	}
}
