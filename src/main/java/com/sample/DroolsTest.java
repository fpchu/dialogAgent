package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


import java.util.HashMap;
import java.util.ArrayList;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            
            // test 1
            AppraisalResult ar1 = new AppraisalResult();
            TextPlan tp1 = new TextPlan();
            
            ar1.setObservedFeautre("Kind to animals");
            ar1.setTarget("Amy");
            ar1.setFacing("Amy");
            ar1.updateFieldFromAppraisalFrame("inv", 0.8); // involvement
            ar1.updateFieldFromAppraisalFrame("dis", 0.0); // dissimilarity
            ar1.updateFieldFromAppraisalFrame("ethicPos", 0.8);
            ar1.updateFieldFromAppraisalFrame("sim", 0.8);
            ar1.updateFieldFromAppraisalFrame("Action", 1.0); // Let say 1 stand for approach
            kSession.insert(ar1);
            //kSession.insert(tp1);
            kSession.fireAllRules();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
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
		
		public Double getFieldFromAppriasalFrame(String field) {
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
		
		private HashMap<String, String> wish;
		
		private ArrayList<String> remains;

		public TextPlan() {
			
			description = new HashMap<String, String>();
			
			description.put("Sub", null);
			
			description.put("Verb", null);
			
			description.put("Obj", null);
			
			
			wish = new HashMap<String, String>();
			
			wish.put("Sub", "I");
			
			wish.put("Verb", null);
			
			wish.put("Obj", null);
			
			remains = new ArrayList<String>();
		}
		
		public HashMap<String, String> getDescription() { return description; }
		
		public void setDescription(String part, String content) {
			description.replace(part, content); }
		
		public HashMap<String, String> getAction() { return wish; }
		
		public void setWish(String part, String content) {
			wish.replace(part, content); }
		
		public ArrayList<String> getRemains() { return remains; }
		
		public void setRemains(String s) {
			remains.add(s); }
	}

}
