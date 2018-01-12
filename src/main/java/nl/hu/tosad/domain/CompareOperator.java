package nl.hu.tosad.domain;

import java.util.ArrayList;

public class CompareOperator extends Operator{
		private String type;
		private String value1;
		private String value2;

		public CompareOperator(String value1, String value2, String type) {
			super();
			this.type = type;
			this.value1=value1;
			this.value2=value2;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getValue1() {
			return value1;
		}

		public void setValue1(String value1) {
			this.value1 = value1;
		}

		public String getValue2() {
			return value2;
		}

		public void setValue2(String value2) {
			this.value2 = value2;
		}
		

		}