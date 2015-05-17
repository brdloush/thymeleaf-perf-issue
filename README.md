# thymeleaf-perf-issue

## What's this project good for?

It's just a simple & stupid test-case that shows potential performance issue (present in thymeleaf 2.1.4, seems to fixed in 3.0.0)
where you need to work with model containing nested lists and you have to iterate over such model.

** Note: this is not trying to be a serious benchmark! ** I've only made it to be able to have super-simple working project
that's showing the slowness we're experiencing on our real project.

## What scenario is it trying to show?

As an example, I've chosen a scenario of "phone listing": we have 30 phones and want to show 5 groups of features for each phone. 
Finally, each feature group contains 10 features.

So in the end of the day, thymeleaf template is supposed to process out 30 * 5 * 10 = 1500 items, which is not too many. 
The template itself doesn't really do any work, just iterates through all the items and renders out "<span>Man, this is slow..</span>" 
for each item.

## What are the results?
The results for just 100 iterations (i7-3820 @ 3.6Ghz), MEASURING RUN with template 'thymeleaf-template' (the one containing th:remove="tag"):

* 2.1.4: Total time spent processing 100 iterations: 29041 ms, **Processing of single page takes: 290 ms**.
* 3.0.0-SNAPSHOT: Total time spent processing 100 iterations: 196 ms. **Processing of single page takes: 1 ms** !!!

## Can I run the test myself?
Sure, for test with Thymeleaf 2.1.4:
```
mvn clean test
```
or, for Thymeleaf 3.0.0-SNAPSHOT:
```
mvn -Dthymeleaf=3.0.0-SNAPSHOT clean test
```

## What seemed to be the cause of that perf-issue?
Quick profiler snapshot of test with Thymeleaf 2.1.4 sayd that most of the time was spent in NestableNode.computeNextChild method. 
Self time of this method ie 51%, 36% is spent in IdentityCounter.isAlreadyCounted.

One more interesting thing is the number of executions of isAlreadyCounted method. For single iteration (30 * 5 * 10 = 1500 items) 
this method is called 26-million times.

```
org.thymeleaf.util.IdentityCounter.isAlreadyCounted(Object)	42.377525	715 ms (42.4%)	715 ms	26,130,998
```
