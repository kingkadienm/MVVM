# 评论点赞功能说明文档

## 功能概述

本项目实现了一个完整的评论点赞功能，支持以下特性：

### ✨ 核心功能
- **一级评论**：用户可以对内容发表评论
- **二级评论**：用户可以回复其他用户的评论
- **点赞功能**：支持对评论和回复进行点赞/取消点赞
- **折叠展示**：支持评论回复的展开和折叠
- **删除功能**：用户可以删除自己的评论
- **实时更新**：评论、点赞、删除等操作实时更新UI

## 📁 项目结构

### 实体类 (Entity)
```
lib_domain/src/main/java/com/wangzs/lib/domain/entity/
├── Comment.kt              # 评论实体类
├── CommentLike.kt          # 评论点赞实体类
└── CommentRequest.kt       # 评论请求参数类
```

### 事件系统 (Event)
```
lib_base_module/src/main/java/com/wangzs/lib/base/module/event/
├── EventCode.kt            # 事件码定义（添加了CommentCode）
├── KeyCode.kt              # 键值定义（添加了Comment相关键值）
├── RequestCode.kt          # 请求码定义（添加了Comment相关码）
└── comment/
    ├── CommentCrudEvent.kt     # 评论CRUD事件
    ├── CommentLikeEvent.kt     # 点赞事件
    └── CommentExpandEvent.kt   # 展开/折叠事件
```

### 业务逻辑层 (Repository & ViewModel)
```
module_me/src/main/java/com/wangzs/module/me/
├── repository/
│   └── CommentRepository.kt    # 评论数据仓库
└── viewmodel/
    └── CommentViewModel.kt     # 评论业务逻辑
```

### 界面层 (Activity & Adapter)
```
module_me/src/main/java/com/wangzs/module/me/
├── activity/
│   ├── CommentActivity.kt      # 评论主页面
│   └── CommentTestActivity.kt  # 评论功能测试页面
└── adapter/
    ├── CommentAdapter.kt       # 评论列表适配器
    └── CommentTestAdapter.kt   # 测试页面适配器
```

### 布局文件 (Layout)
```
module_me/src/main/res/layout/
├── activity_comment.xml        # 评论页面布局
├── activity_comment_test.xml   # 测试页面布局
├── item_comment.xml           # 评论项布局
├── item_reply.xml             # 回复项布局
└── item_comment_test.xml      # 测试项布局
```

### 资源文件 (Resources)
```
module_me/src/main/res/
├── drawable/                  # 图标资源
│   ├── bg_comment_input.xml   # 输入框背景
│   ├── bg_send_button.xml     # 发送按钮背景
│   ├── ic_like_empty.xml      # 空心点赞图标
│   ├── ic_like_filled.xml     # 实心点赞图标
│   ├── ic_reply.xml           # 回复图标
│   ├── ic_delete.xml          # 删除图标
│   └── ic_default_avatar.xml  # 默认头像
└── values/
    └── colors.xml             # 颜色定义
```

## 🚀 使用说明

### 1. 启动评论功能
有两种方式可以体验评论功能：

#### 方式一：通过测试页面
1. 运行项目
2. 进入"我的"页面
3. 点击"评论功能演示"按钮
4. 选择任意测试项目进入评论页面

#### 方式二：直接调用
```kotlin
// 在任意Activity中调用
CommentActivity.start(context, targetId, title)
```

### 2. 功能操作指南

#### 发表评论
1. 点击页面右上角"写评论"按钮
2. 在弹出的输入框中输入评论内容
3. 点击"发送"按钮

#### 回复评论
1. 点击任意评论下方的"回复"按钮
2. 输入回复内容
3. 点击"发送"按钮

#### 点赞操作
- 点击评论或回复旁边的❤️图标进行点赞/取消点赞
- 点赞后图标变为红色，取消点赞恢复灰色

#### 查看回复
- 对于有回复的评论，会显示"展开 X 条回复"按钮
- 点击可展开查看所有回复
- 再次点击"收起回复"可折叠隐藏

#### 删除评论
- 只有评论作者可以看到删除按钮
- 点击删除按钮可删除自己的评论或回复

## 🔧 技术特点

### 架构设计
- **MVVM架构**：使用ViewModel + LiveData/StateFlow进行数据绑定
- **Repository模式**：数据层抽象，便于测试和维护
- **事件驱动**：使用EventBus进行跨组件通信

### 响应式编程
- 使用Kotlin Coroutines + Flow进行异步处理
- StateFlow管理UI状态，自动更新界面

### UI设计
- **嵌套布局**：主评论包含子评论的递归结构
- **动态展示**：根据数据状态动态显示/隐藏UI元素
- **交互反馈**：点击效果、状态变化等用户体验优化

### 数据管理
- **本地状态管理**：评论列表、展开状态等本地缓存
- **乐观更新**：操作后立即更新UI，提升用户体验
- **错误处理**：网络请求失败时的错误提示和状态回滚

## 📊 数据结构

### Comment实体
```kotlin
data class Comment(
    val id: String?,                    // 评论ID
    val content: String?,               // 评论内容
    val userId: String?,                // 用户ID
    val userName: String?,              // 用户名
    val userAvatar: String?,            // 用户头像
    val createTime: String?,            // 创建时间
    val likeCount: Int = 0,             // 点赞数
    val isLiked: Boolean = false,       // 是否已点赞
    val parentId: String? = null,       // 父评论ID
    val replyToUserId: String? = null,  // 回复的用户ID
    val replyToUserName: String? = null,// 回复的用户名
    val replies: MutableList<Comment>,  // 子评论列表
    val isExpanded: Boolean = false,    // 是否展开
    val hasMoreReplies: Boolean = false,// 是否有更多回复
    val totalReplies: Int = 0           // 总回复数
)
```

## 🎯 扩展功能

### 可以继续添加的功能
1. **@提及功能**：支持@其他用户
2. **表情包支持**：评论中插入表情
3. **图片评论**：支持上传图片评论
4. **评论排序**：按时间、热度等排序
5. **评论搜索**：搜索特定评论内容
6. **举报功能**：举报不当评论
7. **评论审核**：管理员审核机制
8. **实时推送**：新评论实时推送通知

### 性能优化
1. **分页加载**：大量评论时的分页机制
2. **图片懒加载**：头像等图片的懒加载
3. **缓存策略**：评论数据的本地缓存
4. **预加载**：预加载部分回复内容

## 📝 注意事项

1. **权限控制**：删除功能需要验证用户权限
2. **数据验证**：评论内容的长度限制和内容过滤
3. **网络处理**：网络异常时的重试机制
4. **内存管理**：大量评论时的内存优化
5. **状态保存**：页面状态的保存和恢复

## 🔍 调试信息

当前实现使用模拟数据，在实际项目中需要：
1. 替换`CommentRepository`中的模拟数据为真实API调用
2. 添加用户认证和权限验证
3. 实现服务端评论存储和管理
4. 添加评论内容的安全过滤

---

这个评论系统提供了完整的前端交互体验，可以作为实际项目中评论功能的基础实现。