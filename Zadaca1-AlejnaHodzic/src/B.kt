import java.io.File
import kotlin.math.E
import kotlin.require

const val SoftwareEngineer = "Softverski inzenjer"
const val ElectricalEngineer = "Inzinjer elektrotehnike"

interface Osoba{
    fun Identity () : String
    fun Title () : String
}

abstract class Inzenjer(val FirstAndLastName: String,
                        var Title: String,
                        var YearsOfExperience: Int,
                        var Expertise: MutableList<String>): Osoba {
    init {
        require(YearsOfExperience >= 0) { "Years Of Experience must be positive." }
        require(Expertise.isNotEmpty()) { "Expertise of Engineer must not be empty." }
        require(FirstAndLastName.isNotEmpty()) { "Engineer must have first and last name." }
        require(Title.isNotEmpty()) { "Title must not be empty." }
    }

    override fun Identity () : String {return this.FirstAndLastName}
    override fun Title () : String {return this.Title}

    fun EngineerInfo () : String {
        return ("First and last name: ${FirstAndLastName}\nTitle: $Title\nYears of experience: " +
                "$YearsOfExperience\nExpertise: ${Expertise.joinToString(", ")}\n")
    }

    //open fun Info (): String {return ""}
    abstract fun Info() : String

    open fun PrintEngineerInfo () {
        println(this.EngineerInfo())
    }
}

class SoftverskiInzenjer(ImeIPrezime: String,
    //Title: String = "Softverski inzenjer",
                         YearsOfExperience: Int,
                         Expertise: MutableList<String>,
                         var NumberOfProjects : Int) : Inzenjer(
    FirstAndLastName = ImeIPrezime,
    Title = SoftwareEngineer,
    YearsOfExperience = YearsOfExperience,
    Expertise = Expertise) {
    init {
        require(NumberOfProjects >= 0) { "Number of Projects must be positive." }
    }

    fun SuccessRate() : String = when (this.YearsOfExperience) {
        in 0..5 -> if (this.NumberOfProjects > 1) "Very successful" else "Fairly successful"
        in 5..15 -> if (this.NumberOfProjects > 3) "Very successful" else if (this.NumberOfProjects < 1) "Not as successful" else "Fairly successful"
        in 15..30 -> if (this.NumberOfProjects > 5) "Very successful" else if (this.NumberOfProjects < 2) "Not as successful" else "Fairly successful"
        else -> if (this.NumberOfProjects > 7) "Very successful" else if (this.NumberOfProjects < 3) "Not as successful" else "Fairly successful"
    }
    //(this.NumberOfProjects/YearsOfExperience).toString()

    override fun Info () : String {
        return this.EngineerInfo() + ("Number of projects: $NumberOfProjects\n")
    }

    override fun PrintEngineerInfo(){
        println(this.Info())
    }
}

class InzenjerElektrotehnike(ImeIPrezime: String,
    //Title: String = "Inzenjer elektrotehnike",
                             YearsOfExperience: Int,
                             Expertise: MutableList<String>,
                             var NumberOfCertificates: Int) :
    Inzenjer(FirstAndLastName= ImeIPrezime,
        Title = ElectricalEngineer,
        YearsOfExperience = YearsOfExperience,
        Expertise = Expertise) {
    init {
        require(NumberOfCertificates > 0) { "Number of certificates must be positive." }
    }

    fun SuccessRate() : String = when (this.YearsOfExperience) {
        in 0..5 -> if (this.NumberOfCertificates > 1) "Very successful" else "Fairly successful"
        in 5..15 -> if (this.NumberOfCertificates > 2) "Very successful" else if (this.NumberOfCertificates < 2) "Not as successful" else "Fairly successful"
        else -> if (this.NumberOfCertificates > 3) "Very successful" else if (this.NumberOfCertificates < 2) "Not as successful" else "Fairly successful"
    }
    //return (this.NumberOfCertificates/YearsOfExperience).toString()

    override fun Info () : String{
        return this.EngineerInfo() + ("Number of certificates: $NumberOfCertificates\n")
    }

    override fun PrintEngineerInfo(){
        println(this.Info())
    }
}

fun GroupsViaExpertise (Engineers: List<Inzenjer>) : Map<String, MutableList<String>>{

    val Expertise = mutableListOf<String>()
    for (Engineer in Engineers){
        for (EngExpertise in Engineer.Expertise){
            if (!Expertise.contains(EngExpertise)){
                Expertise.add(EngExpertise)
            }
        }
    }

    val FoldLambda = { Accumulator : MutableMap<String, MutableList<String>>, Eng: Inzenjer ->
        if (Eng.YearsOfExperience > 5) {
            for (Exp in Expertise) {
                if (Eng.Expertise.contains(Exp)) {
                    Accumulator.getOrPut(Exp) {mutableListOf<String>()}.add(Eng.Identity())
                }
            }
        }
        Accumulator
    }

    return Engineers.fold(mutableMapOf<String, MutableList<String>>(),FoldLambda)
}


fun MostExperiencedEngineers(Engineers: List<Inzenjer>) : Map<String, Inzenjer> {

    val SoftwareEngs = mutableListOf<Inzenjer>()
    val ElectricalEngs = mutableSetOf<Inzenjer>()
    for (Eng in Engineers){
        if (Eng.Title == SoftwareEngineer){
            SoftwareEngs.add(Eng)
        } else if (Eng.Title == ElectricalEngineer) {
            ElectricalEngs.add(Eng)
        }
    }

    var ReduceLambda = {Accumulator : Inzenjer, Eng: Inzenjer ->
        if (Accumulator.YearsOfExperience < Eng.YearsOfExperience) {
            Eng
        } else Accumulator
    }

    val SoftwareEngineer = SoftwareEngs.reduce(ReduceLambda)
    val ElectricalEngineer = ElectricalEngs.reduce(ReduceLambda)

    return mapOf(SoftwareEngineer.Title to SoftwareEngineer, ElectricalEngineer.Title to ElectricalEngineer)
}


fun NumberOfProjectsAndCertificates(Engineers : List<Inzenjer>) : Int {

    val NumberOfProjectsAndCertificates = Engineers.groupingBy {it.Title}.aggregate{
            Key : String, Accumulator : Int?, Element : Inzenjer, _ ->
        if (Key == SoftwareEngineer){
            (Accumulator ?: 0) + (Element as SoftverskiInzenjer).NumberOfProjects
        } else if (Key == ElectricalEngineer){
            (Accumulator ?: 0) + (Element as InzenjerElektrotehnike).NumberOfCertificates
        } else Accumulator ?: 0
    }

    return (NumberOfProjectsAndCertificates[SoftwareEngineer] ?: 0)+ (NumberOfProjectsAndCertificates[ElectricalEngineer] ?: 0)
}

fun InfoOnAllEngineers(Engineers: List<Inzenjer>) {
    for (Engineer in Engineers){
        Engineer.PrintEngineerInfo()
    }
}

fun main() {
    val Engineers = listOf<Inzenjer>(
        SoftverskiInzenjer(ImeIPrezime = "Alan Turing",
            YearsOfExperience = 15,
            Expertise = mutableListOf<String>("Cryptography", "Math", "Algorithms"),
            NumberOfProjects = 7),
        InzenjerElektrotehnike(ImeIPrezime = "Nikola Tesla",
            YearsOfExperience = 65,
            Expertise = mutableListOf("Electrical engineering", "AC systems", "Resonance and oscillation systems"),
            NumberOfCertificates = 5),
        InzenjerElektrotehnike(ImeIPrezime = "John Doe",
            YearsOfExperience = 2,
            Expertise = mutableListOf("F#", "Matlab"),
            NumberOfCertificates = 4),
        InzenjerElektrotehnike(ImeIPrezime = "Rowan Atkinson",
            YearsOfExperience = 6,
            Expertise = mutableListOf("Electrical engineering", "Circuit Design"),
            NumberOfCertificates = 2),
        SoftverskiInzenjer(ImeIPrezime = "Jane Doe",
            YearsOfExperience = 3,
            Expertise = mutableListOf("F#", "C++", "Assembly", "Git"),
            NumberOfProjects = 2),
        SoftverskiInzenjer(ImeIPrezime = "Dennis Ritchie",
            YearsOfExperience = 44,
            Expertise = mutableListOf("Operating Systems", "Assembly", "C"),
            NumberOfProjects = 10),
        SoftverskiInzenjer(ImeIPrezime = "Linus Torvalds",
            YearsOfExperience = 34,
            Expertise = mutableListOf("Git", "Operating Systems", "C", "C++", "Assembly"),
            NumberOfProjects = 5),
    )

    InfoOnAllEngineers(Engineers)

    val ExpertiseGroups = GroupsViaExpertise(Engineers)
    var ExperienceLessThan6 = false

    //u grupama ne bi trebali da se nadju John Doe i Jane Doe
    for ((Expertise, EngineersGroup) in ExpertiseGroups) {
        println("$Expertise: ${EngineersGroup.joinToString(", ")}")
        if (EngineersGroup.contains("John Doe") || EngineersGroup.contains("Jane Doe"))
            ExperienceLessThan6 = true
    }
    print("\n")
    if (ExperienceLessThan6) {
        println("Finding expertise groups via fold function went wrong!")
    }

    val MostExperienced = MostExperiencedEngineers(Engineers)
    var WrongMostExperiencedEngineer = false
    for ((TypeOfEngineer, MostExperiencedEngineer) in MostExperienced) {
        println("Most experienced $TypeOfEngineer:\n${MostExperiencedEngineer.Info()}")
        if (MostExperiencedEngineer.Identity() != "Nikola Tesla" && MostExperiencedEngineer.Identity() != "Dennis Ritchie") {
            WrongMostExperiencedEngineer = true
        }
    }
    print("\n")
    if (WrongMostExperiencedEngineer) {
        println("Finding most experienced engineers using reduce function went wrong!")
    }

    val TotalProjsAndCertificates = NumberOfProjectsAndCertificates(Engineers)
    println("Total number of projects and certificates amongst forwarded engineers is $TotalProjsAndCertificates")
    if (TotalProjsAndCertificates != 35) {
        println("Finding total number of projects and certificates using aggregate function went wrong!")
    }
}