package nl.hu.tosad.webservices;

import nl.hu.tosad.domain.CompareOperator;
import nl.hu.tosad.domain.RangeOperator;

public abstract class AbstractFactory {
	   RangeOperatorFactory getRangeOperator(String min, String max, String type);
	   CompareOperatorFactory getCompareOperator(String value, String type) ;
	}
