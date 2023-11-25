package com.parsla.PeopleHub;

import com.parsla.PeopleHub.entity.configuration.SalaryComponent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PeopleHubApplicationTests {

	@Test
	void contextLoads() {
		double baseSal = 10000;
		SalaryComponent component = new SalaryComponent();
		component.setFormula(true);
		//component.setValue("( [100] + ( [{BASE SALARY}] * [0.1] ) )");
		component.setValue(" ( [{BASE SALARY}] * [0.1] ) ");
		double ss = getAmount1(component,baseSal);
	}


	private double getValue(String orgValue, double baseSal){
		String value = orgValue.trim();
		if(value.startsWith("[")){
			int index = value.indexOf(']');
			String str = value.substring(1,index);
		}
	}

	private double getAmount1(SalaryComponent component, double baseSal) {
		//String ss= "( [100] + ( [{BASE SALARY}] * [0.1] ) )";
		//              [100] + ( [{BASE SALARY}] * [0.1] )
		// ( [{BASE SALARY}] * [0.1] )

		if (component.isFormula()) {
			String value = component.getValue().trim();
			boolean isStart = value.startsWith("(");
			boolean isEnd = value.endsWith(")");
			if (!isStart || !isEnd) {
				throw new RuntimeException("Formula is not correct");
			}
			//String fullFormula = value.substring(0,value.length());
			value = value.replace("{BASE SALARY}", String.valueOf(baseSal)).trim();
			value = value.substring(1,value.length()-1);
			getValue(value, baseSal);
			char[] array = value.toCharArray();
			for (int i = 1; i < array.length - 1; i++) {
				char ans =array[i];
				if (array[i] == '(') {
					int startIndex = value.indexOf('(');
					int lastIndex = value.indexOf(')');
					String formula = value.substring(startIndex, lastIndex);
					String leftOutFormula = value.substring(lastIndex);

					System.out.println(formula);
				}

				if (array[i] == '[') {
					int startIndex = value.indexOf('[');
					int lastIndex = value.indexOf(']');
					String formula = value.substring(startIndex, lastIndex);
					String result = isFormula(formula);
					if(result == null){
						result = isValue(formula);
						System.out.println(result);
					}
					System.out.println(result);
				}

			}
		}
		return 0.0;
	}

	private String isValue(String inputStr){
		inputStr = inputStr.trim();
		boolean isStart = inputStr.startsWith("{");
		if (isStart) {
			return null;
		}

		char[] array = inputStr.toString().toCharArray();
		StringBuilder value = new StringBuilder();
		for (int i = 1; i < array.length ; i++) {
			if(array[i] != ']' || array[i] != ')' || array[i] != '}'){
				value.append(array[i]);
			}
			else {
				throw new RuntimeException("Formula is not correct");
			}
		}
		return value.toString();
	}
	private String isFormula(String inputStr){
		inputStr = inputStr.trim();
		boolean isStart = inputStr.startsWith("{");
		if (!isStart) {
			return null;
		}
		StringBuilder value = new StringBuilder();
		char[] array = value.toString().toCharArray();
		for (int i = 1; i < array.length ; i++) {
			if(array[i] != '}'){
				value.append(array[i]);
			}
		}
		return value.toString();
	}
}
