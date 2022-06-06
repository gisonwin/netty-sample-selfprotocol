#编译java语言
```shell
./protoc.exe -I ./ --java_out=../src/main/java/ ./common-query.proto
```


#编译C++语言
```shell
./protoc.exe -I ./ --cpp_out=./c++/ ./common-query.proto
```
#编译Go语言  (need protoc-gen-go)
```shell
./protoc.exe -I ./ --go_out=./c++/ ./common-query.proto
```
#或者一起编译
```shell
./protoc.exe -I ./ --java_out=./java/ --cpp_out=./c++/ --python_out=./python/ ./common-query.proto
```
