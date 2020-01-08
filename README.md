# DividerLineProject
### demo.apk在demo目录下
## [Demo.apk下载](https://raw.githubusercontent.com/zhongruiAndroid/DividerLineProject/master/demo/demo.apk "apk文件")

```java
//第一个参数：Context 
//第二个参数：item分割间距(默认为0)
//第三个参数：color或者drawable  设置分割线的颜色或者图片样式

//(此时默认使用系统android.R.attr.listDivider属性下的分割效果)
BaseItemDivider baseDividerGridItem = new BaseItemDivider(this);

//有分割线间距，间距区域透明无颜色
BaseItemDivider baseDividerGridItem = new BaseItemDivider(this, 30);

//有分割线间距，分割线有颜色或图片样式
BaseItemDivider baseDividerGridItem = new BaseItemDivider(this, 30,color);
BaseItemDivider baseDividerGridItem = new BaseItemDivider(this, 30,drawable);

//设置水平方向item之间的间隔
baseDividerGridItem.setHGap(hGap);
//设置垂直方向item之间的间隔
baseDividerGridItem.setVGap(vGap);

//LinearLayoutManager下是否显示第一个分割线(默认false)
baseDividerGridItem.setShowFirstLine(showTop);

//LinearLayoutManager下是否显示第后一个分割线(默认false)
baseDividerGridItem.setShowLastLine(showBottom);

//等价setShowFirstLine(true)和setShowLastLine(true);
baseDividerGridItem.setShowBoth();

//不管是哪种LayoutManager不需要额外判断,只需要执行这个方法,内部自动判断是否横向纵向以及反向
recyclerView.addItemDecoration(baseDividerGridItem);
```
#### 瀑布流item目前只提供设置间距,没设置颜色


<img src="https://github.com/zhongruiAndroid/DividerLineProject/blob/master/image/image.jpg" alt="image" width="auto" height="640" >
