<template>
    <div>
        <!-- 引入Header组件 -->
        <Header></Header>
        <div class="m-content">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
               <div class="m-input">
                   <div>
                       <el-form-item label="标题" prop="title">
                           <el-input v-model="ruleForm.title"></el-input>
                       </el-form-item>
                   </div>
                   <div>
                       <el-form-item label="摘要" prop="description">
                           <el-input type="textarea" v-model="ruleForm.description"></el-input>
                       </el-form-item>
                   </div>
               </div>
                <div  class="mavonEditor">
                    <el-form-item label="内容" prop="content">
                        <mavon-editor v-model="ruleForm.content"></mavon-editor>
                    </el-form-item>
                </div>
                <div class="m-button">
                    <el-form-item>
                        <el-button type="primary" plain @click="submitForm('ruleForm')">发表</el-button>
                        <el-button type="danger" plain @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <!-- 引入Footer组件 -->
        <Footer></Footer>
    </div>
</template>

<script>
    //引入header组件
    import Header from "../components/Header"
    import Footer from "../components/Footer";
    export default {
        name: "BlogEdit",
        components:{Footer, Header},
        data() {
            return {
                ruleForm: {
                    id: '', //用于区分'发表'与'编辑'
                    title: '',
                    description: '',
                    content: ''
                },
                rules: {
                    title: [
                        { required: true, message: '请输入标题名称', trigger: 'blur' },
                        { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: '请输入摘要信息', trigger: 'blur' },
                        { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入文章内容', trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        this.$axios.post("/blog/edit",this.ruleForm,{
                            headers:{
                                "Authorization": localStorage.getItem("token")
                            }
                        }).then(res=>{
                            console.log(res)
                            //弹框提示
                            this.$alert('操作成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    //跳转到文章列表页
                                    _this.$router.push("/blog")
                                }
                            });
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        //页面加载时回显文章数据:用于文章编辑
        created() {
            const blogId = this.$route.params.blogId
            const _this = this
            if(blogId){
                this.$axios.get("/blog/"+blogId).then(res=>{
                    const blog = res.data.data
                    _this.ruleForm.id = blog.id
                    _this.ruleForm.title = blog.title
                    _this.ruleForm.description = blog.description
                    _this.ruleForm.content = blog.content

                })
            }
        }
    }
</script>

<style scoped>
    /*内容样式*/
    .m-content{
    }
    /*输入框样式*/
    .m-input{
        max-width: 650px;
    }
    /*按钮样式*/
    .m-button{
        margin-left: 472px;
    }
    /*markdown编辑器样式*/
    .mavonEditor{
        padding-top: 20px;
        width: 1200px;
    }
</style>
