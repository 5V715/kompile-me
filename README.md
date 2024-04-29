# Kotlin Compiler Issue with Later introduced Context

extending a interface and later introducing a 'context' via 
override appears to break the compiler backend.

trying to compile the code provided will cause (with 1.9.3)
version 2.0.0-RC2 gives error that nothing would be overridden - see below

```
org.jetbrains.kotlin.backend.common.BackendException: Backend Internal error: Exception during IR lowering
File being compiled: /Users/silas.schwarz/Documents/learn-stuff/kompile-me/src/main/kotlin/dev/silas/TestImpl.kt
The root cause java.lang.RuntimeException was thrown at: org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen.generate(FunctionCodegen.kt:51)
	at org.jetbrains.kotlin.backend.common.CodegenUtil.reportBackendException(CodegenUtil.kt:253)
	at org.jetbrains.kotlin.backend.common.CodegenUtil.reportBackendException$default(CodegenUtil.kt:237)
	at org.jetbrains.kotlin.backend.common.phaser.PerformByIrFilePhase.invokeSequential(performByIrFile.kt:65)
	at org.jetbrains.kotlin.backend.common.phaser.PerformByIrFilePhase.invoke(performByIrFile.kt:52)
	at org.jetbrains.kotlin.backend.common.phaser.PerformByIrFilePhase.invoke(performByIrFile.kt:38)
	at org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase.phaseBody(CompilerPhase.kt:147)
	at org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase.invoke(CompilerPhase.kt:94)
	at org.jetbrains.kotlin.backend.common.phaser.CompositePhase.invoke(PhaseBuilders.kt:29)
	at org.jetbrains.kotlin.backend.common.phaser.CompositePhase.invoke(PhaseBuilders.kt:16)
	at org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase.phaseBody(CompilerPhase.kt:147)
	at org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase.invoke(CompilerPhase.kt:94)
	at org.jetbrains.kotlin.backend.common.phaser.CompilerPhaseKt.invokeToplevel(CompilerPhase.kt:43)
	at org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory.invokeCodegen(JvmIrCodegenFactory.kt:361)
	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.runCodegen(KotlinToJVMBytecodeCompiler.kt:347)
	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.compileModules$cli(KotlinToJVMBytecodeCompiler.kt:122)
	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.compileModules$cli$default(KotlinToJVMBytecodeCompiler.kt:43)
	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:165)
	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:50)
	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:104)
	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:48)
	at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:101)
	at org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner.runCompiler(IncrementalJvmCompilerRunner.kt:463)
	at org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner.runCompiler(IncrementalJvmCompilerRunner.kt:62)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.doCompile(IncrementalCompilerRunner.kt:477)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compileImpl(IncrementalCompilerRunner.kt:400)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compileNonIncrementally(IncrementalCompilerRunner.kt:281)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compile(IncrementalCompilerRunner.kt:125)
	at org.jetbrains.kotlin.daemon.CompileServiceImplBase.execIncrementalCompiler(CompileServiceImpl.kt:657)
	at org.jetbrains.kotlin.daemon.CompileServiceImplBase.access$execIncrementalCompiler(CompileServiceImpl.kt:105)
	at org.jetbrains.kotlin.daemon.CompileServiceImpl.compile(CompileServiceImpl.kt:1624)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at java.rmi/sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:360)
	at java.rmi/sun.rmi.transport.Transport$1.run(Transport.java:200)
	at java.rmi/sun.rmi.transport.Transport$1.run(Transport.java:197)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:712)
	at java.rmi/sun.rmi.transport.Transport.serviceCall(Transport.java:196)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport.handleMessages(TCPTransport.java:587)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0(TCPTransport.java:828)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.lambda$run$0(TCPTransport.java:705)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:399)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run(TCPTransport.java:704)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	at java.base/java.lang.Thread.run(Thread.java:833)
Caused by: java.lang.RuntimeException: Exception while generating code for:
FUN BRIDGE name:doSomething visibility:public modality:OPEN <> ($this:dev.silas.TestImpl, foo:dev.silas.FirstParameter, bar:dev.silas.SecondParameter, $completion:kotlin.coroutines.Continuation<kotlin.Unit>) returnType:kotlin.Any? [suspend]
  $this: VALUE_PARAMETER INSTANCE_RECEIVER name:<this> type:dev.silas.TestImpl
  VALUE_PARAMETER BRIDGE name:foo index:0 type:dev.silas.FirstParameter
  VALUE_PARAMETER BRIDGE name:bar index:1 type:dev.silas.SecondParameter
  VALUE_PARAMETER BRIDGE name:$completion index:2 type:kotlin.coroutines.Continuation<kotlin.Unit>
  EXPRESSION_BODY
    RETURN type=kotlin.Nothing from='public open fun doSomething (foo: dev.silas.FirstParameter, bar: dev.silas.SecondParameter, $completion: kotlin.coroutines.Continuation<kotlin.Unit>): kotlin.Any? [suspend] declared in dev.silas.TestImpl'
      CALL 'public open fun doSomething ($context_receiver_0: dev.silas.Context, foo: dev.silas.FirstParameter, bar: dev.silas.SecondParameter, $completion: kotlin.coroutines.Continuation<kotlin.Unit>): kotlin.Any? [suspend] declared in dev.silas.TestImpl' type=kotlin.Any? origin=BRIDGE_DELEGATION
        $this: GET_VAR '<this>: dev.silas.TestImpl declared in dev.silas.TestImpl.doSomething' type=dev.silas.TestImpl origin=null
        $context_receiver_0: TYPE_OP type=dev.silas.Context origin=IMPLICIT_CAST typeOperand=dev.silas.Context
          GET_VAR 'foo: dev.silas.FirstParameter declared in dev.silas.TestImpl.doSomething' type=dev.silas.FirstParameter origin=null
        foo: TYPE_OP type=dev.silas.FirstParameter origin=IMPLICIT_CAST typeOperand=dev.silas.FirstParameter
          GET_VAR 'bar: dev.silas.SecondParameter declared in dev.silas.TestImpl.doSomething' type=dev.silas.SecondParameter origin=null
        bar: TYPE_OP type=dev.silas.SecondParameter origin=IMPLICIT_CAST typeOperand=dev.silas.SecondParameter
          GET_VAR '$completion: kotlin.coroutines.Continuation<kotlin.Unit> declared in dev.silas.TestImpl.doSomething' type=kotlin.coroutines.Continuation<kotlin.Unit> origin=null

	at org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen.generate(FunctionCodegen.kt:51)
	at org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen.generate$default(FunctionCodegen.kt:43)
	at org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generateMethodNode(ClassCodegen.kt:390)
	at org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generateMethod(ClassCodegen.kt:407)
	at org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generate(ClassCodegen.kt:169)
	at org.jetbrains.kotlin.backend.jvm.FileCodegen.lower(JvmPhases.kt:41)
	at org.jetbrains.kotlin.backend.common.phaser.FileLoweringPhaseAdapter.invoke(PhaseBuilders.kt:120)
	at org.jetbrains.kotlin.backend.common.phaser.FileLoweringPhaseAdapter.invoke(PhaseBuilders.kt:116)
	at org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase.phaseBody(CompilerPhase.kt:147)
	at org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase.invoke(CompilerPhase.kt:94)
	at org.jetbrains.kotlin.backend.common.phaser.PerformByIrFilePhase.invokeSequential(performByIrFile.kt:62)
	... 44 more
Caused by: java.lang.IllegalArgumentException: No argument for parameter VALUE_PARAMETER CONTINUATION_CLASS name:$completion index:3 type:kotlin.coroutines.Continuation<kotlin.Unit>:
CALL 'public open fun doSomething ($context_receiver_0: dev.silas.Context, foo: dev.silas.FirstParameter, bar: dev.silas.SecondParameter, $completion: kotlin.coroutines.Continuation<kotlin.Unit>): kotlin.Any? [suspend] declared in dev.silas.TestImpl' type=kotlin.Any? origin=BRIDGE_DELEGATION
  $this: GET_VAR '<this>: dev.silas.TestImpl declared in dev.silas.TestImpl.doSomething' type=dev.silas.TestImpl origin=null
  $context_receiver_0: TYPE_OP type=dev.silas.Context origin=IMPLICIT_CAST typeOperand=dev.silas.Context
    GET_VAR 'foo: dev.silas.FirstParameter declared in dev.silas.TestImpl.doSomething' type=dev.silas.FirstParameter origin=null
  foo: TYPE_OP type=dev.silas.FirstParameter origin=IMPLICIT_CAST typeOperand=dev.silas.FirstParameter
    GET_VAR 'bar: dev.silas.SecondParameter declared in dev.silas.TestImpl.doSomething' type=dev.silas.SecondParameter origin=null
  bar: TYPE_OP type=dev.silas.SecondParameter origin=IMPLICIT_CAST typeOperand=dev.silas.SecondParameter
    GET_VAR '$completion: kotlin.coroutines.Continuation<kotlin.Unit> declared in dev.silas.TestImpl.doSomething' type=kotlin.coroutines.Continuation<kotlin.Unit> origin=null

	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitCall$handleValueParameter(ExpressionCodegen.kt:603)
	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitCall(ExpressionCodegen.kt:619)
	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitCall(ExpressionCodegen.kt:134)
	at org.jetbrains.kotlin.ir.expressions.IrCall.accept(IrCall.kt:26)
	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitReturn(ExpressionCodegen.kt:1018)
	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitReturn(ExpressionCodegen.kt:134)
	at org.jetbrains.kotlin.ir.expressions.IrReturn.accept(IrReturn.kt:26)
	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitExpressionBody(ExpressionCodegen.kt:955)
	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.visitExpressionBody(ExpressionCodegen.kt:134)
	at org.jetbrains.kotlin.ir.expressions.IrExpressionBody.accept(IrExpressionBody.kt:26)
	at org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen.generate(ExpressionCodegen.kt:228)
	at org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen.doGenerate(FunctionCodegen.kt:122)
	at org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen.generate(FunctionCodegen.kt:47)
	... 54 more

```


running with 2.0.0-RC2:
```
e: file:///Users/silas.schwarz/Documents/learn-stuff/kompile-me/src/main/kotlin/dev/silas/SomeInterface.kt:16:9 'doSomething' overrides nothing.
```