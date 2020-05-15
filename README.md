# Libgdx-rich
## rich game
### TileMapEditor version:1.1.1


### tmx 图层
landBuilding:展示建筑物的图层,建筑物逐级别升级的图层
<br>
landBase:地基图层，展示地基属于哪一个玩家，购买土地的时候更新，或者土地所有者更变的时候
<br>
ground:背景
<br>
road:道路图层，玩家行走的道路
waypoint:对象图层，包含了大量的对象，来指定每个路点的位置熟悉，是和road对应
landpoint:对象图层，包含了大量的对象，来执行对应的land的位置，是和landBase对应


landPointArray 二维数组来保存所有的landpoint的x,y,row,col
wayPointArray 二维数组来保存所有的waypoint的x,y,row,col,以及属性字段里面对应的landpoint的位置

