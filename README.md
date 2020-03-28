### 简介

arcgis compressed geometry 是一种编码过的线图形格式。线图形经过编码后，体积变小，可以高效的在网络中进行传输。

compressed geometry常用于arcgis网络数据集的路径查询和导航相关接口的返回结果中。

compressed geometry的格式为形如“+0+1+3+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4|+5rg+uq+r9+au+168”或是“+1m91-6fkfr+202tp+k+f+7+3+34+2d”的字符串。

组成线图形的点，其坐标格式除支持x和y坐标之外，同时也支持z和m坐标。

### 如何解码

对于compressed geometry字符串，其解码方式如下。

1. 字符串起始的“+0”表示格式为arcgis compressed geometry的新版本格式。如起始字符串不是“+0”的话，则表示采用的格式为旧版本，直接跳到步骤4即可。
2. 紧接着的“+1”，为版本号，当前阶段只能为1。
3. 再后面的“+3”，标识了点的坐标格式。0表示，只有x和y坐标，1表示具有x、y、z坐标，2表示具有x、y、m坐标，3表示具有x、y、z、m坐标。
4. 去除以上相关的元数据信息之外，剩余的字符串为“+1+emjd+3j07m+3+0+0+1-3-1|+9og+0+lv4+0+lv4|+5rg+uq+r9+au+168”。
5. 根据步骤3中的说明，此图形中包含有x、y、z、m坐标。其中xy坐标在一起进行存储，z坐标和m坐标独立存储，xy坐标、z坐标、m坐标之间使用“|”进行分隔。根据此规则对上述的字符串进行拆分，则xy坐标串为“+1+emjd+3j07m+3+0+0+1-3-1”，z坐标串为“+9og+0+lv4+0+lv4”，m坐标串为“+5rg+uq+r9+au+168”。
6. 对于xy坐标，其格式为”+factor+startX+startY+differX1+differY1+differX2+differY2..."。其中，factor为一个因子，存储的真实坐标为x / factor或是y / factor。其中，第一个点的x坐标为startX，后续点与前面点的差值为differX。比如，第三个点的真实坐标为(startX+differX1+differX2)  / factor。这里需要注意两点：
  1. factor、startX、startY等数字在编码字符串中是使用的32进制进行存储的，解码的时候需要转换成10进制表达形式。
  2. startX、startY、differX、differY等可以是负值。如果是正值，在编码字符串中表现为“+”，否则表现为“-”。
7. 对于z和m坐标，解码方式类似。与xy坐标的差异在于，z和m是独立进行存储的，因此只存在一个start和一个differ，其余部分完全一致。

<!-- more -->

### 代码说明

有了以上对编码方式的说明之后，就可以进行编码来对坐标进行解码了。

```java
public static Line decode(String geometry) {
        String[] parts = geometry.split("\\|");
        String first = parts[0];
        if (first.startsWith("+0")) {
            int flag = Integer.parseInt(first.substring(5, 6));
            int version = Integer.parseInt(first.substring(3, 4));
            if (version != 1) {
                throw new IllegalArgumentException("Compressed geometry: Unexpected version.");
            }
            if (flag > 3) {
                throw new IllegalArgumentException("Compressed geometry: Invalid flags.");
            }
            first = first.substring(6);
            Line line = decodeXy(first);
            if (flag == 0) {
                return line;
            }
            if ((flag & 1) == 1) {
                BiConsumer<Point, Double> consumer = Point::setZ;
                decode(line, partas[1], consumer);
            }
            if ((flag & 2) == 2) {
                BiConsumer<Point, Double> consumer = Point::setM;
                decode(line, parts[parts.length - 1], consumer);
            }
            return line;
        } else {
            return decodeXy(first);
        }
    }
```

以上的处理代码中，首先判定字符串的起始两个字符。如果为“+0”的话，则表示了需要解码的compressed geometry为新格式，可以包含Z或是M坐标，否则为老格式，只需要进行x和y的解码即可。

除此之外，还进行了version和flag的判断。在值不合法的情况下，直接抛出IllegalArgumentException异常。

对xy坐标的解码逻辑如下。

```java
private static Line decodeXy(String part) {
        List<String> xys = extract(part);
        int factor = Integer.parseInt(xys.get(0), 32);
        int differenceX = 0;
        int differenceY = 0;
        Point[] points = new Point[(xys.size() - 1) / 2];
        int inx = 0;
        for (int i = 1; i < xys.size(); i = i + 2) {
            int x = Integer.parseInt(xys.get(i), 32);
            int y = Integer.parseInt(xys.get(i + 1), 32);
            differenceX += x;
            differenceY += y;
            points[inx++] = new Point(differenceX * 1.0 / factor, differenceY * 1.0 / factor);
        }

        return new Line(points);
    }

 private static List<String> extract(String part) {
        Matcher matcher = pattern.matcher(part);
        List<String> xys = new ArrayList<>();
        while (matcher.find()) {
            String p1 = matcher.group(1);
            String p2 = matcher.group(2);
            if ("-".equals(p1)) {
                xys.add("-" + p2);
            } else {
                xys.add(p2);
            }
        }
        return xys;
    }

```

在以上逻辑中，使用了正则表达式来进行字符串相关部分的提取。

```java
private static final Pattern pattern = Pattern.compile("([+-])([^+-]+)");
```



需要关注的是，在模式匹配过程中，如果匹配到的是“-”字符，则表示当前的数字是负值。

```java
if ("-".equals(p1)) {
                xys.add("-" + p2);
            } else {
                xys.add(p2);
            }
```

对于z坐标或是m坐标的处理逻辑大致相同。

```java
private static void decode(Line line, String part, BiConsumer<Point, Double> consumer) {
        List<String> items = extract(part);
        int factor = Integer.parseInt(items.get(0), 32);
        int difference = 0;
        int inx = 0;
        for (int i = 1; i < items.size(); i++) {
            int value = Integer.parseInt(items.get(i), 32);
            difference += value;
            consumer.accept(line.getPoints()[inx++], difference * 1.0 / factor);
        }
    }
```