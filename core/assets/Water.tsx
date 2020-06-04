<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.3.4" name="Water" tilewidth="321" tileheight="481" tilecount="5" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <image width="320" height="169" source="water.png"/>
 </tile>
 <tile id="1">
  <image width="321" height="481" source="cube.png"/>
 </tile>
 <tile id="20">
  <properties>
   <property name="isOpen" type="bool" value="true"/>
  </properties>
  <image width="320" height="480" source="doorOpen.png"/>
 </tile>
 <tile id="23">
  <properties>
   <property name="isOpen" type="bool" value="true"/>
  </properties>
  <image width="320" height="480" source="doorRightOpen.png"/>
 </tile>
 <tile id="24">
  <properties>
   <property name="isOpen" type="bool" value="false"/>
  </properties>
  <image width="320" height="480" source="door.png"/>
 </tile>
</tileset>
