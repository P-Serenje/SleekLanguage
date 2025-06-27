; ModuleID = 'sleek'
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

declare i32 @input()
declare double @inputfloat()
declare i8* @inputstring()
declare void @print_int(i32)
declare void @print_float(double)
declare void @print_string(i8*)

define i32 @add(i32 %a, i32 %b) {
entry:
  %a.addr = alloca i32
  store i32 %a, i32* %a.addr
  %b.addr = alloca i32
  store i32 %b, i32* %b.addr
  %t0 = load i32, i32* %a.addr
  %t1 = load i32, i32* %b.addr
  %t2 = add i32 %t0, %t1
  ret i32 %t2
  ret i32 0
}

define i32 @main() {
entry:
  %t3 = call i32 @input()
  %x.addr = alloca i32
  store i32 %t3, i32* %x.addr
  %t4 = call i32 @input()
  %y.addr = alloca i32
  store i32 %t4, i32* %y.addr
  %t5 = load i32, i32* %x.addr
  %t6 = load i32, i32* %y.addr
  %t7 = call i32 @add(i32 %t5, i32 %t6)
  %z.addr = alloca i32
  store i32 %t7, i32* %z.addr
  %t8 = load i32, i32* %z.addr
  call void @print_int(i32 %t8)
  ret i32 0
}
