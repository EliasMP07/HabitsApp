# Aplicación OfflineFirst para Gestión de Hábitos con Autenticación
Esta aplicación está diseñada para la gestión de hábitos con una estrategia "OfflineFirst", garantizando que las funcionalidades clave estén disponibles incluso sin conexión a internet. La arquitectura del proyecto está basada en principios de Clean Architecture y el patrón de diseño MVVM (Model-View-ViewModel), los principios SOLID, utilizando las últimas tecnologías y herramientas de desarrollo para Android.
## Tecnologías y Herramientas Utilizadas
-Jetpack Compose: Para la creación de interfaces de usuario modernas y reactivas.
- FirebaseAuth: Para la autenticación segura de usuarios.
- RealTimeDatabase: Para el almacenamiento y sincronización de datos en tiempo real.
- WorkManager: Para la gestión de tareas en segundo plano.
- AlarmManager: Para la programación de alertas y recordatorios.
- Retrofit: Para las comunicaciones de red y consumo de APIs.
- Hilt: Para la inyección de dependencias, simplificando la gestión de componentes.
- Room: Para el almacenamiento de datos local, permitiendo una estrategia OfflineFirst.
- Coroutines: Para la programación asíncrona, facilitando operaciones concurrentes.
- NavigationCompose: Para la navegación entre pantallas utilizando Jetpack Compose.
- Material Design: Para garantizar una interfaz de usuario intuitiva y consistente.
- Coil: Para la carga y manipulación eficiente de imágenes.

## Requisitos

- Android Studio Iguana
- Gradle Version 8.3
- Kotlin 1.9.23 o superior.

## Estructura del proyecto
-core: Modulo que contiene funcionalidades compartidas entre diferentes módulos de la aplicación.
    -presentation: Contiene los componentes composables que se reutilizaran en toda la pantalla.
-authentication: Modulo que contiene todas las pantallas relacionadas a la autenticacion
    -data: Contiene el repositorio donde se hace la creacion de usuario, logeo y validador de email
    -di: Contiene el modulo que inyecta los repositorios, las usescases, el matcher y el firebaseAuth
    -domain: Contiene los casos de uso, repitorio.
    -presentation: Contiene las pantallas, viewmodel, eventos y state para la vista.
-home: Modulo que contiene todas las pantallas relacionadas a home.
    -data: Contiene el repositorio donde se hace la creacion de notificaciones y habitos, la alarm manager para las alertas en segundo plano
           la base de datos local, el workmanager y los mappers.
    -di: Contiene el modulo que inyecta los repositorios, las usescases, retrofit, room, el work manager y alarmManager
    -domain: Contiene los casos de uso, repitorio y models.
    -presentation: Contiene las pantallas, viewmodel, eventos y state para la vista.
-onboarding: Modulo que contiene las pantallas relacionado al onboarding.
    -data: Contiene el repositorio donde se guardar la datapreferences del usuario para que no se vuelva a mostrar el onboarding.
    -di: Contiene el modulo que inyecta la datapreferences.
    -domain: Contiene los casos de uso, repitorio.
    -presentation: Contiene las pantallas, viewmodel, eventos y state para la vista.
-settings: Modulo que contiene la pantalla de ajustes y el cierre de sesión.
    -presentation:Contiene las pantallas, viewmodel, eventos y state para la vista.
navigation: Modulo que contiene toda la navegacion de pantallas.



## Estructura de la app
![Image text](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/structureproject.png)

# HabitsApp

## Onboarding Screens

![Onboarding 1](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/onboarding1.png) ![Onboarding 2](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/onboarding2.png) ![Onboarding 3](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/onboarding3.png)

![Onboarding 4](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/onboarding4.png)

## Authentication Screens

![Authentication Login](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/authenticationlogin.png) ![Authentication Register](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/authenticationregister.png)

## Home Screens

![Home Habits](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/home_habits.png) ![Home Edit Habit](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/home_edit_habit.png) ![Home Create Habit](https://github.com/EliasMP07/HabitsApp/blob/master/app/src/main/assets/home_create_habit.png)


