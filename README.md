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
road:道路图层，玩家行走的道路 waypoint:对象图层，包含了大量的对象，来指定每个路点的位置熟悉，是和road对应 landpoint:对象图层，包含了大量的对象，来执行对应的land的位置，是和landBase对应

landPointArray 二维数组来保存所有的landpoint的x,y,row,col wayPointArray 二维数组来保存所有的waypoint的x,y,row,col,以及属性字段里面对应的landpoint的位置

### 所有的起点都是从左下角开始

    /**
     * 路点和地点的数组的存储是为[row][col]
     * 在编辑器里面对应的位置为 col 从左到右  0-->10
     * 在编辑器里面对应的位置为 row 从下往上  0-->10
     */
    lateinit var wayPointArray: Array<Array<WayPoint?>>
    lateinit var landPointArray: Array<Array<LandPoint?>>
    /**
    * 在tiledLayer获取cell的接口里面
     * getCell(x,y)
     * x = 从左到右
     * y = 从下到上
    */
    layer!!.getCell(point.col, point.row).setTile(tile)
    另外的editor里面编辑的时候在waypoint对象层指定对应的landpoint时
    保证 land_col = 从左到右 ;lan_row = 从下往上
    