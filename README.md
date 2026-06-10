# OrbitCycle Mobile

## Descrição
OrbitCycle Mobile é um aplicativo Android para monitoramento de missões de coleta de lixo espacial, visualização de métricas operacionais e acompanhamento de eventos relacionados a satélites e detritos espaciais.

## Objetivo
Fornecer uma interface móvel para equipes operacionais e stakeholders acompanharem missões em tempo real, analisar métricas de desempenho, receber alertas de eventos críticos e consultar histórico de operações.

## Tecnologias utilizadas
- Kotlin
- Jetpack Compose
- Android SDK
- Material Design 3
- AndroidX (Lifecycle, Navigation)
- Retrofit (API)

## Pré-requisitos
- Android Studio
- JDK 21 ou superior
- Android SDK com API level alvo do projeto (consultar file build.gradle)
- Gradle wrapper (incluso no repositório)
- Dispositivo Android com USB Debugging (para execução em dispositivo físico) ou AVD configurado

## Instruções de instalação
1. Clone o repositório:
     git clone https://github.com/ericaokamura/space-connect-mobile
2. Entre na pasta do projeto:
     cd space-connect-mobile
3. Abra no Android Studio (ver abaixo) e aguarde a sincronização/Build do Gradle.

## Como abrir o projeto no Android Studio
- Inicie o Android Studio → File → Open → selecione a pasta raiz do projeto (onde está o settings.gradle).
- Permita que o Gradle sincronize e instale dependências.
- Se solicitado, atualize o SDK/Plugins recomendados.

## Como executar em emulador Android
1. Abra o AVD Manager no Android Studio e crie/execute um emulador compatível.
2. Selecione o módulo `app` e clique em Run (▶) ou escolha o emulador na lista de dispositivos.
3. Aguarde a build e instalação no emulador.

## Como executar em dispositivo físico
1. Habilite "Developer options" e "USB debugging" no dispositivo.
2. Conecte via USB ou use ADB over network.
3. Aceite permissão de depuração no dispositivo.
4. Selecione o dispositivo no Android Studio e clique em Run (▶).
