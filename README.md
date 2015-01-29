# thymeleaf-perf-issue

Simple stupid test-case that shows potential performance issue in thymeleaf where you need to work with model containing nested lists.

As an example, I've chosen a scenario of "phone listing": we have 30 phones and want to show 5 groups of features for each phone. Finally, each feature group contains 10 features.

So in the end of the day, thymeleaf template is supposed to process out 30 * 5 * 10 = 1500 items, which is not too many. The template itself doesn't really do any work, just iterates through all the items and renders out "<span>Man, this is slow..</span>" for each item.

**The results?** (i7-3820 @ 3.6Ghz)

Total time spent processing 2868 ms, **Processing of single page takes 286 ms**

Quick profiler snapshot says that most of the time was spent in NestableNode.computeNextChild method. Self time of this method ie 51%, 36% is spent in IdentityCounter.isAlreadyCounted
