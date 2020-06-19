<template>
    <div>
        <!-- 引入Header组件 -->
        <Header></Header>
        <el-container>
            <el-main>
                <div class="m-blog">
                    <p class="m-header-title">{{ blog.title }}</p>
                    <div>
                        <el-divider><i class="el-icon-edit"></i></el-divider>
                    </div>
                    <div class="markdown-body" v-html="blog.content"></div>
                </div>
            </el-main>
        </el-container>
        <!-- 引入Footer组件 -->
        <Footer></Footer>
    </div>
</template>

<script>
    //引入header组件
    import Header from "../components/Header";
    //引入github-markdown-css
    import 'github-markdown-css';
    import Footer from "../components/Footer";
    export default {
        name: "BlogDetail",
        components:{Footer, Header},
        data(){
            return{
                blog:{
                    id: '',
                    title:'',
                    content:''
                },
                islogin:false
            }
        },
        //页面加载时即回显数据
        created() {
            const blogId = this.$route.params.blogId
            console.log(blogId)
            const _this = this
            if(blogId){
                this.$axios.get("/blog/"+blogId).then(res=>{
                    const blog = res.data.data
                    _this.blog.id = blog.id
                    _this.blog.title = blog.title
                    //将文章内容(markdown)渲染为html格式
                    var MarkdownIt = require('markdown-it');
                    var md = new MarkdownIt();
                    var result = md.render(blog.content);
                    _this.blog.content = result

                    //若当前博客页面的用户ID等于当前登录用户的ID时,则展示'编辑'及'删除'按钮
                    _this.islogin = (blog.userId===_this.$store.getters.getUserInfo.id)
                    //alert(_this.$store.getters.getUserInfo.id)
                    //alert(blog.userId)
                    //alert(_this.islogin)
                })
            }
        }
    }
</script>

<style scoped>
 /*文章内容页面样式*/
 .m-blog{
     box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
     width: 1100px;
     min-height: 600px;
     margin: 0 auto;
     padding: 20px 40px;
 }
 /*文章标题样式*/
 .m-header-title{
     text-align: center;
     font-size:30px;
     font-family: 'Dubai Light';
     padding-top: 25px;
 }
 /*文章内容样式*/
 .markdown-body{
     margin-top: 120px;
 }
</style>
