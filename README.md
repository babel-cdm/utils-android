# ASYNC JOB

- Creando una instancia de AsyncJob

	new AsyncJob.AsyncJobBuilder<Boolean>()
	        .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
	            @Override
	            public Boolean doAsync() {
	                // Do some background work
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                return true;
	            }
	        })
	        .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
	            @Override
	            public void onResult(Boolean result) {
	                Toast.makeText(context, "Result was: " + result, Toast.LENGTH_SHORT).show();
	        }
	}).create().start();    

- Utilizando un método estático:

	AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
	    @Override
	    public void doOnBackground() {

	        // Pretend it's doing some background processing
	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Create a fake result (MUST be final)
	        final boolean result = true;

	        // Send the result to the UI thread and show it on a Toast
	        AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
	            @Override
	            public void doInUIThread() {
	                Toast.makeText(context, "Result was: "+ result, Toast.LENGTH_SHORT).show();
	            }
	        });
	    }
	});

- Encoloar diferentes tareas asincornas, hasta que no acabe una no comenzará otra.
 
	// Create a job to run on background
	AsyncJob.OnBackgroundJob job = new AsyncJob.OnBackgroundJob() {
	    @Override
	    public void doOnBackground() {
	        // Pretend to do some background processing
	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // This toast should show a difference of 1000ms between calls
	        AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
	            @Override
	            public void doInUIThread() {
	                Toast.makeText(context, "Finished on: "+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
	            }
	        });
	    }
	};

	// This ExecutorService will run only a thread at a time
	ExecutorService executorService = Executors.newSingleThreadExecutor();

	// Send 5 jobs to queue which will be executed one at a time
	for(int i = 0; i < 5; i++) {
	    AsyncJob.doInBackground(job, executorService);
	}


# ENCRYPTAATION

- Inicializar módulo de encriptación:

	EncryptationManager.config()
	    .setAESKey("7x-_2bIjMSp2IYCxGw4&o5K1xG#4X#SQ");

- Encriptar, los metodos lanzan la excepcion EncryptException:

	Encrypt encrypt = EncryptationManager.getEncrypt();
	encrypt.encryptAES(value);
	encrypt.decryptAES(value);
	encrypt.ecryptMD5(value);


# SECURE DATA

NOTAS:

1) PASSWORD debe de ser indicado por el usuario, actualmente se esta buscando una mejor solución para esto.
2) SecureData.init() tarda entre 200-500ms dependiendo del dispositivo.
3) SALT KEY es guardado como un texto plano actualmente. Se esta buscando una mejor solución para esto.

---------------------------------

- Inicializar el entorno seguro, necesario iniciar un callback ya que pueden llevar entre 200-400ms inicializar el entorno:

	SecureData.init(context, PASSWORD, new SecureData.Callback() {
	    @Override
	    public void onSuccess() {
	    }

	    @Override
	    public void onFail(Exception e) {
	    }
	});

- Inicializar SecureData en modo no seguro, en dispositivos donde no sea compatible AES y PBE algorithm se inciará en este modo de forma automática:

	SecureData.initWithoutEncryption(context);

- Guardar:

SecureData.put(key, T);
SecureData.put(key, List<T>);

- Guardar varios elementos a la vez:

	SecureData.chain()
     .put(KEY_LIST, List<T>)
     .put(KEY_ANOTHER,"test")
     .commit();

- Obtener:
	T result = SecureData.get(key);
	T result = SecureData.get(key, T); // Indicando valor por defecto

- Eliminar
	SecureData.remove(key);

- Eliminar varios elementos a la vez:
	SecureData.remove(KEY_LIST, KEY_NAME);	

- Comprobar si existe un elemento:
	boolean contains = SecureData.contains(key);	







# OTHERS

Logger: Clase complementaria para log.

	- Configurar nivel de log a mostrar:
		LoggerManager.config().setShowLevel(Type.ALL);

	- Como usar:
		Logger log = LoggerManager.get();

		log.print(TAG_APP, TAG_CLASS, txt); // Por defecto el typo es INFO.
		log.print(Type, TAG_APP, TAG_CLASS, txt);

Images: Clase para el tratamiento de imagenes.
	- Como usar:
        ImagesManager.get().[Function];	

Network: Clase para comprobar el estado de la red.
	- Como usar:
		NetworkManager.get().haveNetworkConnection()

Timer: Clase que ejecutar un temporizador.
	- Como usar:
		new Timer().startTimer(3000, this);
		"this" corresponde al listener para cuando acabe el timer. Puede ser cualquier clase que implemente la interfaz
		OnFinishedTimer
	- Resultado
		Cuando el timer acaba se ejecuta el método timerFinished() en el listener

navigation: Control de la navegación entre fragments
 	// Pediente de indicar ejemplos.	

root.RootManager: Para comprobar si el dispositivo esta rooteado o no. (No es 100% efectivo)

AndroidVersion: Funciones de ayuda para comprobar la versión del sistema operativo sobre el que esta corriendo la aplicación.

Screen: Clase que comprueba las propiedades de la pantalla del dispositivo sobre el que esta corriendo la aplicación.

XSSSaniter: Clase para limpiar un string y no contenga sentencias javascrit. 
	- Como usar:
		XSSSaniter.stripXSS(txt);

Others: Diferentes utilidades: 
	Verificar un email.
	Verificar DNI/NIE.
	Generar aleatorios.
	Comprobar si hay tarjeta SIM.
	Obtener el ID del dispositivo.
	Generar un UUID. 		

