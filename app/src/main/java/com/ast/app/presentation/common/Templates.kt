package com.ast.app.presentation.common

class Templates {
//    Column(
//    modifier = Modifier
//    .fillMaxSize()
//    .padding(dimensionResource(id = R.dimen.padding_m)),
//    horizontalAlignment = Alignment.Start,
//    verticalArrangement = Arrangement.Top,
//    ) {
//        val welcomeText = buildAnnotatedString {
//            withStyle(
//                style = SpanStyle(
//                    color = MaterialTheme.colorScheme.primary,
//                    fontWeight = FontWeight.Medium,
//                )
//            ) {
//                append("Welcome to\n")
//            }
//            withStyle(
//                style = SpanStyle(
//                    fontWeight = FontWeight.Light,
//                    fontSize = 28.sp,
//                )
//            ) {
//                append("Advanced Study Tutorials")
//            }
//        }
//        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xl)))
//        Text(text = welcomeText, style = MaterialTheme.typography.displaySmall)
//    }
}


//for using room database with encryption for login sessions
// Define an Entity for the authentication data
//@Entity(tableName = "user")
//data class User(
//    @PrimaryKey val id: String,
//    val username: String,
//    val authToken: String
//)
//
//// Define a DAO (Data Access Object) to interact with the database
//@Dao
//interface UserDao {
//    @Query("SELECT * FROM user LIMIT 1")
//    suspend fun getUser(): User?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUser(user: User)
//}
//
//// Create a Room Database instance with encryption
//@Database(entities = [User::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}
//
//// Use the Room Database in your application
//val database = Room.databaseBuilder(
//    context,
//    AppDatabase::class.java, "app-database"
//).build()
//
//// Save user data to the database
//val userDao = database.userDao()
//val user = User(id = "1", username = "john_doe", authToken = "your_auth_token")
//userDao.insertUser(user)
//
//// Retrieve user data from the database
//val storedUser = userDao.getUser()
