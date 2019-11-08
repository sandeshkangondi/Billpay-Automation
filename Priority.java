package com.intuit.bpui_qa;

import java.lang.annotation.Target;
public @interface Priority {
	int value() default 4;
}