# gatlingloadtest
### Usage
1. Update Scenarios.scala as per your requirement<br>
2. mvn gatling:execute -Dusers=1000 -Dduration=60 -DbaseUrl=http://localhost:6767<br>
or pass your own Simulation Name after creating under scala/loadtest. Default name is "LoadTest".<br>
2. mvn gatling:execute -Dusers=1000 -Dduration=60 -DbaseUrl=http://localhost:6767 -Dgatling.simulation.name=<your simulation name>

### Reference
https://gatling.io/docs/2.3/general/simulation_setup/<br>
https://gatling.io/docs/2.3/general/scenario/

