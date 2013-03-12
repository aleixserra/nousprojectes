class Jutge extends Thread {
	// instanciar variables
	pista atletisme;
	// Constructor
	public Jutge(pista pisto) {
		this.atletisme = pisto;
	}
	public void run (){
		while (atletisme.contadorCarrils == atletisme.carril);
		atletisme.dispar = true;
		System.out.println("bang!");
	}
}

class Participant extends Thread {
	// Instanciar variables
	double i = 0;
	int numerocarril;
	pista atletisme;
	String nomParticipant;
	int esgotament;
	int estimulant;
	
	// Constructor
	public Participant(String str, pista pisto, int esgot, int estimu) {
		this.nomParticipant = str;
		this.atletisme = pisto;
		this.esgotament = esgot;
		this.estimulant = estimu;
	}
	// Methods
	private double control(double metresrecorreguts){
		
		double augment = (double) (Math.random() * 2);
		if (augment>1.5)
			incrementarEsgotament();		
		return augment;
		//prioritat actual?
		
	}
	private void incrementarEsgotament(){
		if (this.esgotament<3){
			System.out.println("en la pista  el corredor "+ this.nomParticipant +" ha fet una esprintada que passarà factura");
			this.esgotament++;			
		}
		this.setPriority(5 - this.esgotament);
	}
	private void incrementarEstimulant(int increment){
		if (increment==2 && this.getPriority()<9 || increment==1 && this.getPriority()<10){
			this.setPriority(this.getPriority() + increment);
		}
		
	}
	public void run() {
		int numerocarril = atletisme.getCarril(this.getName());
		if(numerocarril != -1){
			System.out.println(this.nomParticipant + "<< Esta Corrent en numero carril: " +  numerocarril);
			while (!atletisme.dispar){};
			while (i < atletisme.getLongitud()) {

				i += this.control(i);
				System.out.println(i + " " + this.nomParticipant);
								try {
					this.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.incrementarEsgotament();
			atletisme.trencarCinta();
			atletisme.setClasificat(this.nomParticipant, this.esgotament, this.estimulant);
			if (atletisme.classificatsn[0].equals(nomParticipant)){
				System.out.println("en la pista ... el corredor " + this.nomParticipant + " ha arribat el primer classificant-se així per a la final. (al primer de la pista al acabar)");		
				incrementarEstimulant(2);
				
			}else if (atletisme.classificatsn[1].equals(nomParticipant)){		
				System.out.println("en la pista ... el corredor " + this.nomParticipant + " ha quedat en segona posició classificant-se així per a la final (al arribar el segon)");
				incrementarEstimulant(1);
			}
			else{				
				System.out.println("en la pista ... el corredor " + this.nomParticipant + " ha acabat la cursa");
				System.out.println();
			}
		}else{
			System.out.println("Tu no Corres!");
		}
	}

}

class pista {
	
	//instanciar variables
	
	int 		llargada;
	int 		carril;   // Numero de carrils	
	int 		contadorCarrils = 0;
	String[] 	llistaCorredors;
	//variables per les classificacions
	String[] 	classificatsn;
	int[] 		classificatsc;
	int[] 		classificatsm;
	int 		contaclasificats = 0;
		
	//boolean carrilsPlens;
	boolean guanyador;
	boolean segon;
	volatile boolean dispar=false;
	//constructor
	pista(int longi, int caril){
		llargada = longi;
		carril = caril;
		llistaCorredors = new String [carril];
		classificatsn = new String [carril];
		classificatsm = new int[carril];
		classificatsc = new int [carril];
		
		//carrilsPlens = false;
		guanyador = true;
		segon = true;
		int numClassificats = caril;
	}

	//metodes
	synchronized int getCarril(String Nom){
		if (contadorCarrils < carril){
			llistaCorredors[contadorCarrils] = Nom;
			return contadorCarrils++;
			
		}
		else {
			System.out.println("Atencio carrils plens");
			return -1;
		}
	}
	int getLongitud() {
		return llargada;
	}
	synchronized int trencarCinta(){
		if (guanyador){
			guanyador = false;
			return 1;
		}
		else if (segon){
			segon = false;
			return 2;
		}
		else
			return 3;
	}
	void setClasificat(String nomfinalista, int cansament, int motivacio){
		classificatsn[contaclasificats] = nomfinalista;
		classificatsc[contaclasificats] = cansament;
		classificatsm[contaclasificats] = motivacio;		
		contaclasificats++;
		
	}
}	

public class Cursaa {
	public static void main(String args[]) {
		
		pista Cursa1 = new pista(40, 5);
		pista Cursa2 = new pista(40, 5);
		Jutge jutge = new Jutge(Cursa1);
		Jutge jutga = new Jutge(Cursa2);
		
		
		Participant Corredor = new Participant("Jordi", Cursa1, 0, 0);
		Participant Corredor2 = new Participant("Juan", Cursa1, 0, 0);
		Participant Corredor3 = new Participant("Jalbert", Cursa1, 0, 0);	
		Participant Corredor4 = new Participant("Mario", Cursa1, 0, 0);	

		
		Participant Corredor5 = new Participant("Maria", Cursa2, 0, 0);	
		Participant Corredor6 = new Participant("Luigi", Cursa2, 0, 0);	
		Participant Corredor7 = new Participant("Jhonny", Cursa2, 0, 0);	
		Participant Corredor8 = new Participant("Jardi", Cursa2, 0, 0);	

		Corredor.start();
		Corredor2.start();
		Corredor3.start();
		Corredor4.start();
		Corredor5.start();
		Corredor6.start();
		Corredor7.start();
		Corredor8.start();
		jutge.start();
		jutga.start();
		

	}
}

