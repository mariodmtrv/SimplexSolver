#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include <math.h>

#define MX 100
#define M_RAZM 30
#define SUM 0
#define RAZ 1
#define PRO 2
#define DEL 3

typedef struct {long chl,znm;} rac;

int n,m,n_act;
long prost[MX];
rac mat[M_RAZM][M_RAZM];
int basis[M_RAZM];
rac beta[M_RAZM][2];
rac obj[3][M_RAZM];
rac cost[3][M_RAZM];
rac fn_val[5];
FILE *f;
char fn[13];

gen_pr()
   {
       long i,j,k,l;
       char c;
       for(i=0;i<MX;i++) prost[i] = 0;
       i = 0;
       j = 2;
       while(i < MX)
          {
             c = 1;
             for(k=0;k<i-1;k++)
                {
                   l = (long) prost[k]*prost[k];
                   if(l > (long)j) {c = 1; break;}
                   if(j % prost[k] == 0) {c = 0; break;}
                }
             if(c == 1)  prost[i++] = j;
             j++;
          }
   }

sakr(a)
  rac *a;
    {
       long p,mn,ac,az;
       ac = labs(a[0].chl);
       az = labs(a[0].znm);
       mn = min(ac,az);
       for(p=0;p<mn;p++)
          {
	     if(prost[p] > mn)
                 break;
             if(ac % prost[p] == 0 && az % prost[p] == 0)
                {
                   ac /= prost[p];
                   az /= prost[p];
                   mn /= prost[p];
                   p--;
                }
          }
       if(a[0].chl == 0) { a[0].znm = 1; return; }
       if((a[0].chl < 0 && a[0].znm > 0) || (a[0].chl > 0 && a[0].znm < 0))
          {
             a[0].chl = -ac;
             a[0].znm =  az;
          }
       else
          {
             a[0].chl = ac;
             a[0].znm = az;
          }
    }

rac sum_razl(r1,r2,oper)
  rac r1,r2;
  int oper;
    {
       rac rez;
       long i,mn,x,y,z;
       x = labs(r1.znm);
       y = labs(r2.znm);
       mn = min(x,y);
       z = 1;
       for(i=0;i<mn;i++)
          {
	     if(prost[i] > mn)
                  break;
             if(x % prost[i] == 0 && y % prost[i] == 0)
                {
                   x /= prost[i];
                   y /= prost[i];
                   z *= prost[i];
                   mn /= prost[i];
                   i--;
                }
          }
       rez.znm = x*y*z;
       if(oper == SUM)
         { rez.chl = y*r1.chl + x*r2.chl; }
       else
         { rez.chl = y*r1.chl - x*r2.chl; }
       sakr(&rez);
       return(rez);
    }

rac um_dn(r1,r2,oper)
   rac r1,r2;
   int oper;
      {
        rac t1,t2,rez;
        long x,y;
        t1.chl = r1.chl;
        t1.znm = (oper == PRO ? r2.znm : r2.chl);
        sakr(&t1);
        t2.chl = (oper == PRO ? r2.chl : r2.znm);
        t2.znm = r1.znm;
        sakr(&t2);
        rez.chl = t1.chl * t2.chl;
        rez.znm = t1.znm * t2.znm;
        if(rez.znm < 0)
          {
             rez.chl = -rez.chl;
             rez.znm = -rez.znm;
          }
        return(rez);
      }

check_for_one(col)
  int col;
   {
      int j,i=-1;
      do {
         i++;
         if(i==m) break;
      } while (mat[i][col].chl==0);
      if(i==m) return;
      if (mat[i][col].chl==1) {
         j = i;
         do {
            j++;
            if(j==m) {basis[i] = col; break;}
         } while (mat[j][col].chl==0);
      }
   }

input()
   {
      int i,j;
      long l;
      for (i=0;i<M_RAZM;i++) {
         for (j=0;j<M_RAZM;j++) {
	    mat[i][j].chl = 0;
	    mat[i][j].znm = 1;
         }
         for (j=0;j<2;j++) {
            beta[i][j].chl = 0;
            beta[i][j].znm = 1;
            obj[j][i].chl = 0;
	    obj[j][i].znm = 1;
	    cost[j][i].chl = 0;
	    cost[j][i].znm = 1;
         }
         obj[2][i].chl = 0;
	 obj[2][i].znm = 1;
	 cost[2][i].chl = 0;
	 cost[2][i].znm = 1;
      }
      printf("Enter data file name: ");
      scanf("%s",fn);
      f = fopen(fn,"r+");
      fscanf(f,"%d",&n);
      fscanf(f,"%d",&m);
      for (i=0;i<2;i++) {
         for (j=0;j<n;j++) {
            fscanf(f,"%ld",&l);
            obj[i][j].chl = l;
         }
      }
      for(i=0;i<m;i++) {
         fscanf(f,"%ld",&l);
         beta[i][0].chl = l;
         fscanf(f,"%ld",&l);
         beta[i][1].chl = l;
	 for(j=0;j<n;j++)
            {
               fscanf(f,"%ld",&l);
               mat[i][j].chl = l;
            }
      }
      fclose(f);
   }

init_mat()
   {
      int i,j,k;
/*    for (i=0;i<m;i++) {
         if(beta[i][0].chl < 0)
             {
                beta[i][0].chl = -beta[i][0].chl;
                beta[i][1].chl = -beta[i][1].chl;
                for (j=0;j<n;j++) {
		   mat[i][j].chl = -mat[i][j].chl;
                }
             }
      }   */
      for(i=0;i<m;i++) basis[i] = -1;
      for(j=0;j<n;j++) check_for_one(j);
      n_act = n-1;
      for (i=0;i<m;i++) {
	 if (basis[i] == -1) {
            obj[2][n].chl = 1;
            mat[i][n].chl = 1;
            basis[i] = n;
            n++;
         }
      }
      for(i=0;i<n;i++) {
	 for(j=0;j<m;j++) {
	   for(k=0;k<3;k++)
	    cost[k][i].chl += mat[j][i].chl*obj[k][basis[j]].chl;
	 }
	 for(k=0;k<3;k++)
	    cost[k][i].chl -= obj[k][i].chl;
      }
   }

iter(row,col)
  int row,col;
   {
      int i,j;
      rac upr,rab;
      basis[row] = col;
      upr.chl = mat[row][col].chl;
      upr.znm = mat[row][col].znm;
      for(i=0;i<2;i++)
	 beta[row][i] = um_dn(beta[row][i],upr,DEL);
      for(i=0;i<n;i++)
	  mat[row][i] = um_dn(mat[row][i],upr,DEL);
      for(j=0;j<m;j++)
	if(j!=row)
	   {
	      upr.chl = mat[j][col].chl;
	      upr.znm = mat[j][col].znm;
	      for(i=0;i<n;i++)
		 {
		    rab = um_dn(upr,mat[row][i],PRO);
		    mat[j][i] = sum_razl(mat[j][i],rab,RAZ);
		 }
	      for(i=0;i<2;i++) {
		 rab = um_dn(upr,beta[row][i],PRO);
		 beta[j][i] = sum_razl(beta[j][i],rab,RAZ);
	      }
	   }
      for(j=0;j<3;j++)
	 {
	    upr.chl = cost[j][col].chl;
	    upr.znm = cost[j][col].znm;
	    for(i=0;i<n;i++)
	       {
		  rab = um_dn(upr,mat[row][i],PRO);
		  cost[j][i] = sum_razl(cost[j][i],rab,RAZ);
	       }
	 }
   }

calc_fvalue()
   {
      int i,j;
      rac rb1,rb2;
      for(i=0;i<5;i++)
	 {
	    fn_val[i].chl = 0;
	    fn_val[i].znm = 1;
	 }
      for(i=0;i<m;i++)
	 {
	    j = basis[i];
        if(j > n_act)
	       {
		  fn_val[0] = sum_razl(fn_val[0],beta[i][0],SUM);
		  fn_val[1] = sum_razl(fn_val[1],beta[i][1],SUM);
	       }
	    else
	       {
		  rb1 = um_dn(obj[0][j],beta[i][0],PRO);
		  fn_val[2] = sum_razl(fn_val[2],rb1,SUM);
		  rb1 = um_dn(obj[0][j],beta[i][1],PRO);
		  rb2 = um_dn(obj[1][j],beta[i][0],PRO);
		  fn_val[3] = sum_razl(fn_val[3],rb1,SUM);
		  fn_val[3] = sum_razl(fn_val[3],rb2,SUM);
		  rb1 = um_dn(obj[1][j],beta[i][1],PRO);
		  fn_val[4] = sum_razl(fn_val[4],rb1,SUM);
	       }
	 }
   }

print_rac(x,y,r)
  int x,y;
  rac r;
    {
      gotoxy(x,y);
      if(r.znm == 1)  {cprintf("%d",r.chl); return;}
      cprintf("%d",r.chl);
      cprintf("/");
      cprintf("%d",r.znm);
    }

print_t(x,y,r,rt)
   int x,y;
   rac r,rt;
     {
	gotoxy(x,y);
	if(rt.chl == 0)  { print_rac(x,y,r); return; }
	print_rac(x,y,rt);
	cprintf(".t");
	if(r.chl == 0) return;
	if(r.chl > 0)  cprintf("+");
	print_rac(wherex(),y,r);
     }

print_line()
  {
    int i;
    for(i=0;i<79;i++) printf("Ä");
    printf("\n");
  }

print_table(rw,cl)
int rw,cl;
   {
      int i,j;
      char redr;
      if(rw >= 0 && cl >= 0)  { redr = 1; }
      else                    { redr = 0; }
      print_line();
      for(j=0;j<m;j++)
	 {
	    gotoxy(2,wherey());
	    textattr(0x0a);
	    cprintf("%d",j+1);
	    gotoxy(5,wherey());
	    if(redr == 1 && j == rw)  { textattr(0x0e); }
	    else                      { textattr(0x0f); }
	    cprintf("%d",basis[j]+1);
	    print_t(8,wherey(),beta[j][0],beta[j][1]);
	    for(i=0;i<n;i++)
	      {
		if(redr == 1)
		  {
		     if(i == cl || j == rw) { textattr(0x0e); }
		     else                   { textattr(0x0f); }
		     if(i == cl && j == rw) textattr(0x0c);
		  }
		print_rac(19+7*i,wherey(),mat[j][i]);
	      }
	    printf("\n");
	 }
      if(redr == 1) textattr(0x0f);
      print_line();
      for(j=0;j<3;j++)
	 {
	    gotoxy(2,wherey());
	    if(j==0) cprintf("cost");
	    if(j==1) cprintf("t*cost");
	    if(j==2) cprintf("M*cost");
	    for(i=0;i<n;i++)
	      {
		if(redr == 1 && i == cl) { textattr(0x0e); }
		else                     { textattr(0x0f); }
		print_rac(19+7*i,wherey(),cost[j][i]);
	      }
	    printf("\n");
	 }
      print_line();
   }

print_frac(s,on,r,b)
  int s,on;
  rac r;
  char b;
    {
      if(s != 0 && r.chl > 0 && b==1) fprintf(f,"+");
      if(r.chl < 0)           fprintf(f,"-");
      if(on == 1 && r.znm == 1 && labs(r.chl) == 1) return;
      if(r.znm == 1)  {fprintf(f,"%d",labs(r.chl)); return;}
      fprintf(f,"{%d \\over ",labs(r.chl));
      fprintf(f,"%d }",r.znm);
    }

print_ft(r,rt)
   rac r,rt;
     {
    if(rt.chl == 0)  { print_frac(0,0,r,0); return; }
    print_frac(0,1,rt,0);
	fprintf(f,"t");
	if(r.chl == 0) return;
    print_frac(1,0,r,1);
     }


print_fvalue()
   {
      int c = 0;
      if(fn_val[0].chl != 0 || fn_val[1].chl != 0)
	 {
	    if(fn_val[0].chl != 0 && fn_val[1].chl != 0) fprintf(f,"(");
	    print_ft(fn_val[0],fn_val[1]);
	    if(fn_val[0].chl != 0 && fn_val[1].chl != 0) fprintf(f,")");
	    fprintf(f,"M");
	    c = 1;
	 }
      if(fn_val[2].chl != 0) { print_frac(c,0,fn_val[2],c); c = 1; }
      if(fn_val[3].chl != 0)
	{
       print_frac(c,1,fn_val[3],c);
	   fprintf(f,"t");
	   c = 1;
	}
      if(fn_val[4].chl != 0)
	{
       print_frac(c,1,fn_val[4],c);
	   fprintf(f,"t^2");
	   c = 1;
	}
      if(c == 0) fprintf(f,"0");
   }

print_ftable(rw,cl)
 int rw,cl;
   {
    int i,j;
    for(j=0;j<m;j++)
	 {
        i = basis[j]+1;
        fprintf(f,"x_{%d} & ",i);
        if(i > n_act+1) { fprintf(f,"M"); }
        else  { print_ft(obj[0][i-1],obj[1][i-1]); }

        fprintf(f," & ");
	    print_ft(beta[j][0],beta[j][1]);
	    for(i=0;i<n;i++)
	      {
		fprintf(f," & ");
        if(j==rw && i==cl) fprintf(f,"\\fbox{$");
		print_frac(1,0,mat[j][i],0);
        if(j==rw && i==cl) fprintf(f,"$}");
	      }
	    fprintf(f,"\\");
        fprintf(f,"\\");
	    fprintf(f,"\n");
	 }
        fprintf(f,"\\hline  ");
    fprintf(f,"\\overline{c} & \\multicolumn{2}{c|}{");
	    calc_fvalue();
	    print_fvalue();
	fprintf(f,"}");
	    for(i=0;i<n;i++)
        {
        fprintf(f," &  ");
		print_ft(cost[0][i],cost[1][i]);
		if(cost[2][i].chl != 0)
		  {
           print_frac(1,1,cost[2][i],1);
		   fprintf(f,"M");
		  }
        }
	    fprintf(f,"\\");
        fprintf(f,"\\");
	    fprintf(f,"\n \\hline ");
   }

pr_head()
   {
      int i;
      fprintf(f,"\\documentstyle{article}\n");
      fprintf(f,"\\textwidth  6.8in\n");
      fprintf(f,"\\textheight 8.6in\n");
      fprintf(f,"\\oddsidemargin 0.0in\n");
      fprintf(f,"\\evensidemargin 0.0 in\n");
      fprintf(f,"\\begin{document}\n");
      fprintf(f,"\\begin{center}\n");
      fprintf(f,"\\renewcommand{\\arraystretch}{1.4}");
      fprintf(f,"$$");
      fprintf(f,"\\begin{array}{|c|c|c|");
      for(i=0;i<n;i++) fprintf(f,"c");
      fprintf(f,"|}\n");
      fprintf(f,"\\hline  ");
      fprintf(f,"X_b & C_b & \\overline{b}");
      for(i=0;i<n;i++)
        fprintf(f," & x_{%d}",i+1);
      fprintf(f,"\\");
      fprintf(f,"\\ \n");
      fprintf(f,"  &  &  ");
      for(i=0;i<n;i++)
	 {
        fprintf(f," & ");
	    if(obj[2][i].chl != 0)  { fprintf(f,"M"); }
	    else { print_ft(obj[0][i],obj[1][i]); }
	 }
      fprintf(f,"\\");
      fprintf(f,"\\");
      fprintf(f,"\n \\hline  ");
   }

pr_eof()
   {
      fprintf(f,"\\end{array}\n");
      fprintf(f,"$$");
      fprintf(f,"\\end{center}\n");
      fprintf(f,"\\end{document}");
   }

pr_fln()
   {
      int i;
	 gotoxy(1,1);
	 clreol();
	 textattr(0x0b);
	 cprintf("row \\ column");
	 textattr(0x0a);
	 for(i=0;i<n;i++)
	   {
	    gotoxy(19+7*i,1);
	    cprintf("%d",i+1);
	   }
   }

   main()
     {
       int r,c,y,fil;

       gen_pr();
       input();
       init_mat();
       printf("\nPrint tables in file? +/- ");
       fil = getche();
       if(fil == 43)
	  {
	     printf("\nEnter output file name: ");
	     scanf("%s",fn);
	     f = fopen(fn,"w+");
	     pr_head();
	  }
       clrscr();
       gotoxy(1,2);
       do  {
	 print_table(-1,-1);
	 y = wherey();
	 pr_fln();
	 gotoxy(10,y);
	 textattr(0x06);
	 cprintf("row: ");
	 scanf("%d",&r);
	 pr_fln();
	 if(y==25) y--;
	 gotoxy(50,y);
	 textattr(0x06);
	 cprintf("column: ");
	 scanf("%d",&c);
	 r--;
	 c--;
     if(fil == 43) print_ftable(r,c);
	 if(r < 0 || c < 0) break;
	 gotoxy(1,y-m-6);
	 print_table(r,c);
	 y++;
	 gotoxy(1,y);
	 iter(r,c);
       } while(r >= 0 && c >= 0);
       if(fil == 43)
	  {
	     pr_eof();
	     fclose(f);
	  }
       textattr(0x07);
       clrscr();
       return;
     }
