package api.fmi.simplexator.algorithm;

public class Fraction {
private int numerator;
private int denominator;
private int gcd(){
	while(numerator>1 && denominator>1){
		if(numerator>denominator){
			numerator%=denominator;
		}
		else denominator%=numerator;
	}
	return Math.max(numerator,denominator);
	
}

private void reduce() {
	int gcd=gcd();
	numerator/=gcd;
	denominator/=gcd;
}

public Fraction(int num, int denom) {
	numerator=num;
	denominator=denom;
	reduce();
	
}

public Fraction(int num) {
	numerator=num;
	denominator=1;
}

public Fraction addition(Fraction other){
	int num=this.numerator*other.denominator
			+this.denominator*other.numerator;
	
	int denom=this.denominator*other.denominator;
	
	Fraction result=new Fraction(num,denom);
	
	return result;
}

public Fraction substraction(Fraction other){
	// a-b=a+(-b)
	Fraction negother=new Fraction(-other.numerator,other.denominator);
	Fraction result =this.addition(negother);
	
	return result;
}

public Fraction multiply(Fraction other){
	int num=this.numerator*other.numerator;
	int denom=this.denominator*other.denominator;
	
	Fraction result=new Fraction(num,denom);
	
	return result;
}

public Fraction divide(Fraction other){
	// (a/b)/(c/d)=(b/a)*(c/d)
	Fraction result=new Fraction(this.denominator,this.numerator);
	result=result.multiply(other);
	
	return result;
} 

} 
