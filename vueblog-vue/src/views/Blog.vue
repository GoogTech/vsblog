<template>
    <div>
        <!-- 使用header组件 -->
        <Header></Header>
        <div class="m-content">
            <el-container>
                <el-header>
                    <el-input @keyup.enter.native="search" v-model="searchTitle" class="m-search" placeholder="输入关键字后回车来查询指定文章..." prefix-icon="el-icon-search">
                    </el-input>
                </el-header>
                <el-main>
                    <div class="block">
                        <!-- 文章列表 -->
                        <el-card class="box-card" v-for="blog in blogs">
                            <div slot="header" class="clearfix">
                                <span class="m-title">
                                    <router-link class="router-link-active" :to="{name: 'BlogDetail',params: {blogId:blog.id}}">
                                        {{ blog.title }}
                                    </router-link>
                                </span>
                                <!-- 文章编辑,复制链接,删除功能 -->
                                <el-button @click="del(blog.id)" style="float: right;" type="danger" plain icon="el-icon-delete" circle></el-button>
                                <el-button @click="cplink(blog.id)" style="float: right; margin-right: 10px" type="success" plain icon="el-icon-link" circle></el-button>
                                <router-link :to="{name: 'BlogEdit',params:{blogId: blog.id}}">
                                    <el-button style="float: right;" type="primary" plain icon="el-icon-edit" circle></el-button>
                                </router-link>
                            </div>
                            <div class="m-desc">
                                {{ blog.description }}
                            </div>
                            <div class="m-time">
                                <el-tag type="info">{{ blog.created }}</el-tag>
                            </div>
                        </el-card>
                        <!-- 分页 -->
                        <el-pagination class="m-pagination"
                                       background
                                       layout="prev, pager, next"
                                       :current-page="currentPage"
                                       :page-size="pageSize"
                                       :total="total"
                                       @current-change=page>
                        </el-pagination>
                    </div>
                </el-main>
            </el-container>
        </div>
        <!-- 引入footer组件 -->
        <Footer></Footer>
    </div>
</template>

<script>
    //引入header组件
    import Header from "../components/Header";
    import Footer from "../components/Footer";
    // 引入element-ui依赖
    import Element from 'element-ui';

    export default {
        inject:['reload'], //用于刷新页面并重新加载数据
        name: "Blogs",
        components: {Footer, Header},

        //数据回显
        data() {
            return {
                blogs: {}, //博客列表数据
                currentPage: 1, //当前页默认为1
                total: 0, //文章总数
                pageSize: 5, //每页的文章数

                linkUrl: '', //文章链接

                searchTitle: '', //查询输入框中输入的文章名称
            }
        },
        //获取文章数据
        methods: {
            //分页获取文章列表
            page(currentPage) {
                const _this = this
                _this.$axios.get("/blog?currentPage=" + currentPage).then(res => {
                    console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.current
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                })
            },
            //通过id删除指定文章[v]
            del(id){
                //判断用户是否登录:无登录则跳转到登录页
                const token = this.$store.getters.getToken;
                if(token){
                    //弹框提示
                    this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        //通过id删除指定文章
                        this.$axios.get("/blog/delete/"+id).then(res=>{
                            if(res.data.code===200){
                                this.$message({type: 'success', message: '删除成功!'});
                                //刷新页面并重新加载数据
                                this.reload();
                            }else {
                                this.$message({type: 'error', message: '删除失败!'});
                            }
                        })
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }else {
                    this.$router.push("/login")
                }
            },
            //复制文章链接[v]
            dataProcessing (id) {
                this.linkUrl = window.location.href + '/' + id
            },
            cplink: function (id) {
                this.dataProcessing(id)
                const lk = this.linkUrl;
                this.$copyText(this.linkUrl).then(function (e) {
                    Element.Message.success("链接复制成功 : "+lk);
                }, function (e) {
                    Element.Message.success("链接复制失败 !");
                })
            },
            //模糊查询文章
            search(){
                const _this = this
                _this.$axios.get("/blog/findByTitle?title=" + this.searchTitle).then(res => {
                    console.log(res)
                    _this.blogs = res.data.data
                })
            },
        },
        //页面渲染时即调用
        created() {
            this.page(1) //当前页默认为1
        }
    }
</script>

<style scoped>
    /*内容样式*/
    .m-content{
        max-width: 800px;
        margin: 0 auto; /*居中*/
    }
    /*分页插件样式*/
    .m-pagination {
        text-align: center;
    }
    /*标签样式*/
    .m-time {
        margin-right: 10px;
        margin-top: 84px;
    }
    /*文章标题样式*/
    .m-title {
        font-size: 22px;
        font-family: 'Dubai Light';
        text-decoration: none;
    }
    /*文章描述样式*/
    .m-desc {
        font-size: 15px;
        font-family: 'Nirmala UI';
        max-height: 20px;
    }
    /*文章查询输入框样式*/
    .m-search{
        max-width: 693px;
        margin-left: 33px;
    }
    /*文章(card)列表样式*/
    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }
    .box-card {
        width: 700px;
        height: 252px;
        margin: 0 auto 30px;
    }
    /*清除router-link标签生成的下划线*/
    a {
        text-decoration: none;
    }
    .router-link-active {
        text-decoration: none;
    }
</style>
