package ir.atitec.signalgo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import ir.atitec.signalgo.models.JSOGGenerator;

public class RefrenceAnalysor {
    public HashMap<Integer, String> hashMap = new HashMap<>();
    private String json;
    public ArrayList<Integer> values = new ArrayList<>();

    public RefrenceAnalysor(String json) {
        this.json = json;
    }

    public String getFinalJson() {

        if (json.indexOf(JSOGGenerator.REF_KEY) == -1) {
            return json;
        }
        boolean hasId = false;
        int i = 0;
        do {
            hasId = false;
            int index = json.indexOf(JSOGGenerator.ID_KEY, i);
            if (index != -1) {
                int x1 = json.indexOf(":", index);
                x1 = json.indexOf("\"", x1);
                int x2 = json.indexOf("\"", x1 + 1);
                int value = Integer.parseInt(json.substring(x1 + 1, x2));
                hashMap.put(value, findBlock(index));
                values.add(value);
                hasId = true;
                i = index + 1;
            }
        } while (hasId);


        for (int k = 0; k < values.size(); k++) {
            hashMap.put(values.get(k), fillRefrences(hashMap.get(values.get(k)), false));
        }
        json = fillRefrences(json, true);


        return json;
    }


    private String fillRefrences(String json, boolean replaceWithHash) {
        boolean hasRef = false;
        int j = 0;
        do {
            hasRef = false;
            int index = json.indexOf(JSOGGenerator.REF_KEY, j);
            if (index != -1) {
                int x1 = json.indexOf(":", index);
                x1 = json.indexOf("\"", x1);
                int x2 = json.indexOf("\"", x1 + 1);
                int value = Integer.parseInt(json.substring(x1 + 1, x2));
                String str = hashMap.get(value);
                String rep = json.substring(json.lastIndexOf("{", index), json.indexOf("}", index) + 1);
                if (replaceWithHash) {
                    json = json.replace(rep, str);
                    j = index + str.length();
                } else {
                    json = json.replace(rep, "null");
                    j = index + 1;
                }

                hasRef = true;
            }
        } while (hasRef);
        return json;
    }


    private String findBlock(int index) {
        int start = json.lastIndexOf("{", index);
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        int end = 0;
        int i = start + 1;
        while (!stack.empty()) {
            end = json.indexOf("}", i);
            int temp = json.indexOf("{", i);
            if (temp > end || temp == -1) {
                stack.pop();
                i = end + 1;
            } else {
                stack.push(temp);
                i = temp + 1;
            }
        }
        return json.substring(start, end + 1);
    }
}
