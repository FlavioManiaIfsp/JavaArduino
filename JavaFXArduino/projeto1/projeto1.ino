int statusLed = 0;
const int LED = 13;

void setup() {
  Serial.begin(9600);
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);
}

void loop() {
  if(Serial.available() > 0){
    statusLed = Serial.read();
    if(statusLed == '0'){
      digitalWrite(LED, LOW);
      //Serial.println("Desligado");
      //Serial.write("Desligado");
      Serial.println("Desligado");
    }else if(statusLed == '1'){
      digitalWrite(LED, HIGH);
      Serial.println("Ligado");
      //Serial.write("Ligado");
    }    
  } 
}
