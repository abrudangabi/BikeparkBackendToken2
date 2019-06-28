package com.gabi.backend.bikeparkend.service.implementation;

import com.gabi.backend.bikeparkend.controller.requests.BikeparkReservationRequest;
import com.gabi.backend.bikeparkend.controller.requests.ConcursReservationRequest;
import com.gabi.backend.bikeparkend.exceptions.*;
import com.gabi.backend.bikeparkend.model.*;
import com.gabi.backend.bikeparkend.repository.*;
import com.gabi.backend.bikeparkend.repository.BikerRepository;
import com.gabi.backend.bikeparkend.service.GenericService;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.gabi.backend.bikeparkend.config.SecurityConfig;

import javax.annotation.PostConstruct;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GenericServiceImpl implements GenericService {

    private static final int DEFAULT_LIMIT = 20;

    /*@Autowired
    private DataSource dataSource;*/

    @Autowired
    private ItemBasedRecommender recommender;

    /*@Autowired
    private AdministratorRepository administratorRepository;*/

    @Autowired
    private BikeParkRepository bikeParkRepository;

    @Autowired
    private BikerRepository bikerRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ConcursRepository concursRepository;

    @Autowired
    private LocatieRepository locatieRepository;

    @Autowired
    private RezervareBikeParkRepository rezervareBikeParkRepository;

    @Autowired
    private RezervareConcursRepository rezervareConcursRepository;

    @Autowired
    private TraseuRepository traseuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PreferintaRepository preferintaRepository;

    @Autowired
    private SimilaritateRepository similaritateRepository;

    @PostConstruct
    private void loadData() {
        initRoles();
        initDatabase();
       //TODO initParole();
        //generare();
        System.out.println("A salvat in bd");
    }

    private void initRoles(){

    }

    private void initDatabase(){

    }

    public void generare(){
        //Generare Bikeri, Useri, Contact, Roluri, Disciplina, Locatie, Foto

        int nr = 1;
        int i = 1;
            //TODO 1508 useri de adaugat

            //TODO BIKER
            int nrUser = i;
            long nrBiker = i;
            int idUser = i;
            long userSmecher = (long) idUser;
            //User
            User user = new User();
            user.setId(idUser);
            user.setActive(true);
            user.setUsername("user" + nrUser);
            //String parolaCodata = u.getUsername();
            user.setPassword(SecurityConfig.passwordEncoder().encode("user" + nrUser));
            //user.setPassword("pass" + nrUser);
            user.setEmail("user" + nrUser + "@yahoo.com");

            //TODO ID PT BIKER
            Long idBiker = (long) nrBiker;

            //Biker
            Biker biker = new Biker();
            biker.setId(idBiker);
            LocalDate localDate1 = LocalDate.of(2016, Month.FEBRUARY, 3);
            LocalDate localDate2 = LocalDate.of(1997, Month.MAY, 15);
            biker.setNume("biker" + nrUser);
            biker.setPrenume("biker" + nrUser);
            biker.setBicicleta("Canyon");
            biker.setAniExperienta(2);
            biker.setDataNasterii(localDate2);
            biker.setMembruData(localDate1);
            biker.setDisciplinaFavorita(Disciplina.ENDURO);

            biker.setUser(user);
            user.setBiker(biker);

            //Role
            Role role = new Role();
            role.setRoleId(2);
            role.setRoleString(RoleString.BIKER);
            user.addRole(role);

            //Contact
            Contact contact = new Contact();
            contact.setId((long) idUser);
            contact.setPhoneNumber("0770123456");
            contact.setFacebookLink("https://www.facebook.com/dannyhartfanpage/");

            contact.setBiker(biker);
            biker.setContact(contact);

            //Locatie
            Locatie locatie = new Locatie();
            locatie.setId(idUser);
            locatie.setTara("Romania");
            locatie.setProvincie("Cluj");
            locatie.setLocalitate("Clij-Napoca");
            locatie.setLatitude(45.0);
            locatie.setLongitude(25.0);
            locatie.setCodPostal("400000");
            locatie.setStrada("Constantin Brancusi");
            locatie.setNumber("120");

            locatie.setContact(contact);
            contact.setLocatie(locatie);

            //Photo
            Photo photo = new Photo();
            Long idPhoto = (long) idUser;
            photo.setId(idPhoto);
            photo.setUrl("https://www.ucc-sportevent.com/wp-content/uploads/photo-Mega-Dimanche-Cyril-Charpin-9-1080x675.jpg");


            photo.setContact(contact);
            contact.setPhoto(photo);

            //Repository
            userRepository.save(user);
            bikerRepository.save(biker);
            roleRepository.save(role);
            contactRepository.save(contact);
            locatieRepository.save(locatie);
            photoRepository.save(photo);



        //Generare Bikepark, Useri, Contact, Roluri, Disciplina, Locatie, Dificultate, Foto, 1 Traseu


        i = 2;
            //TODO

            //TODO BIKEPARK

            nrUser = i;
            idUser = i;
            long nrBikepark = i;
            //User
            user = new User();
            user.setId(idUser);
            user.setActive(true);
            user.setUsername("user" + nrUser);
            user.setPassword(SecurityConfig.passwordEncoder().encode("user" + nrUser));
            user.setEmail("user" + nrUser + "@yahoo.com");

            //TODO ID PT BIKEPARK
            Long idBikepark = (long) nrBikepark;
            //Bikepark
            BikePark bikePark = new BikePark();
            bikePark.setDenumire("Bikepark " + nrUser);
            bikePark.setNrLocuri(100);
            bikePark.setDescriere("Bikepark care e ideal relaxarii cu bicicleta");
            bikePark.setId(idBikepark);
            bikePark.setTelescaun(true);

            bikePark.setUser(user);
            user.setBikePark(bikePark);

            //Role
            role = new Role();
            role.setRoleId(1);
            role.setRoleString(RoleString.BIKEPARK);
            user.addRole(role);

            //Contact
            contact = new Contact();
            contact.setId((long) idUser);
            contact.setPhoneNumber("0770123456");
            contact.setFacebookLink("https://www.facebook.com/WhistlerBikePark/");
            contact.setWebsite("https://www.whistlerblackcomb.com/plan-your-trip/lift-access/bike-park-passes.aspx");

            contact.setBikePark(bikePark);
            bikePark.setContact(contact);

            //Locatie
            locatie = new Locatie();
            locatie.setId(idUser);
            locatie.setTara("Canada");
            locatie.setProvincie("British Columbia");
            locatie.setLocalitate("Whistler");
            locatie.setLatitude(50.0);
            locatie.setLongitude(123.0);
            locatie.setCodPostal("400000");
            locatie.setStrada("Mountain Square");
            locatie.setNumber("4282 ");

            locatie.setContact(contact);
            contact.setLocatie(locatie);

            //Photo
            photo = new Photo();
            idPhoto = (long) idUser;
            photo.setId(idPhoto);
            photo.setUrl("https://lh5.googleusercontent.com/p/AF1QipPNXft_3I65CozWF08kgIsKJCERDLdpqQopDlYg=w408-h306-k-no");

            photo.setContact(contact);
            contact.setPhoto(photo);

            //Traseu
            long idTraseu = idBikepark + 2;
            Traseu traseu = new Traseu();
            traseu.setId(idTraseu);
            traseu.setLungime((long) 1200);
            traseu.setDenumire("Flow Line");
            traseu.setTipTraseu(Disciplina.ENDURO);
            traseu.setDificultate(Dificultate.mediu);

            bikePark.addTraseu(traseu);

            //Repository
            userRepository.save(user);
            bikeParkRepository.save(bikePark);
            roleRepository.save(role);
            contactRepository.save(contact);
            locatieRepository.save(locatie);
            photoRepository.save(photo);
            traseuRepository.save(traseu);



    }

    private void initParole(){
        List<User> users = userRepository.findAll();

        for(User u : users){
            if(u.getId()>=(long)1 && u.getId()<=100) {
                u.setActive(true);
                userRepository.save(u);
            }
        }

        for(User u : users){
            if(u.getId()>=(long)1 && u.getId()<=100){
                String parolaCodata = u.getUsername();
                u.setPassword(SecurityConfig.passwordEncoder().encode(parolaCodata));
                userRepository.save(u);
            }
        }

        /*for(User u : users){
            Optional<User> applicantOptional = userRepository.findById(u.getId());
            User user = applicantOptional.get();
            String parolaCodata = u.getUsername();
            user.setPassword(SecurityConfig.passwordEncoder().encode(parolaCodata));
        }*/

        System.out.println("A schimbat parolele");
    }

    private void initDefaultContactForBiker(Biker biker) {
        Contact contact = new Contact();
        contact.setFacebookLink("");
        contact.setPhoneNumber("");
        contact.setWebsite("");

        biker.setContact(contact);
        contactRepository.saveAndFlush(contact);
    }

    private void initDefaultAddressForBiker(Contact contact) {
        Locatie locatie = new Locatie();
        locatie.setNumber("");
        locatie.setStrada("");
        locatie.setCodPostal("");
        locatie.setLocalitate("");
        locatie.setTara("");
        locatie.setProvincie("");

        contact.setLocatie(locatie);
        locatieRepository.saveAndFlush(locatie);
    }

    private void initDefaulPhotoForBiker(Contact contact) {

        Photo photo = new Photo();
        contact.setPhoto(photo);

        photoRepository.saveAndFlush(photo);
    }

    private void initDefaultContactForBikepark(BikePark bikePark) {
        Contact contact = new Contact();
        contact.setFacebookLink("");
        contact.setPhoneNumber("");
        contact.setWebsite("");

        bikePark.setContact(contact);
        contactRepository.saveAndFlush(contact);
    }

    private void initDefaultAddressForBikepark(Contact contact) {
        Locatie locatie = new Locatie();
        locatie.setNumber("");
        locatie.setStrada("");
        locatie.setCodPostal("");
        locatie.setLocalitate("");
        locatie.setTara("");
        locatie.setProvincie("");

        contact.setLocatie(locatie);
        locatieRepository.saveAndFlush(locatie);
    }

    private void initDefaulPhotoForBikepark(BikePark bikePark) {

        Photo photo = new Photo();
        bikePark.getContact().setPhoto(photo);
        photoRepository.saveAndFlush(photo);

    }

    @Override
    public List<BikePark> getAllBikeParks(){
        List<BikePark> bikeParks = bikeParkRepository.findAll();
        List<BikePark> maiPutine= new ArrayList<>();
        for(int i = 0; i < 200; i++){
            maiPutine.add(bikeParks.get(i));
        }
        return maiPutine;
        //return bikeParkRepository.findAll();
    }

    @Override
    public List<Biker> getAllBikers(){
        return bikerRepository.findAll();
    }

    @Override
    public List<Locatie> getAllLocations(){
        return locatieRepository.findAll();
    }

    @Override
    public List<Traseu> getAllTrasee(){
        return traseuRepository.findAll();
    }

    @Override
    public List<RezervareBikePark> getAllRezervareBikePark(){
        return rezervareBikeParkRepository.findAll();
    }

    @Override
    public List<Categorie> getAllCategorii(){
        return categorieRepository.findAll();
    }

    @Override
    public List<Concurs> getAllConcurs(){
        return concursRepository.findAll();
    }

    @Override
    public List<RezervareConcurs> getAllRezervareConcurs(){
        return rezervareConcursRepository.findAll();
    }

    @Override
    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    @Override
    public List<Photo> getAllPhotos(){
        return photoRepository.findAll();
    }

    @Override
    public BikePark getBikeparkByUserId(Long id) throws NotValidBikeparkException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (user.get().getBikePark() != null)
                return user.get().getBikePark();
        }
        throw new NotValidBikeparkException("No bikepark with this user ID!");
    }

    @Override
    public BikePark getBikeparkById(Long id) throws NotValidBikeparkException {
        Optional<BikePark> bikePark = bikeParkRepository.findById(id);
        if (bikePark.isPresent()) {
            System.out.println(bikePark.get().toString());
            return bikePark.get();
        }
        throw new NotValidBikeparkException("No bikepark with this user ID!");
    }

    @Override
    public Biker getBikerByUserId(Long id) throws NotValidBikerException, NotAllowedBikerException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {

            if (!checkTheUser(user.get())) {
                throw new NotAllowedBikerException("You don't have permissions to remove this data!");
            }

            if (user.get().getBiker() != null)
                return user.get().getBiker();
        }
        throw new NotValidBikerException("No biker with this user ID!");
    }

    @Override
    public Biker getBikerById(Long id) throws NotValidBikerException, NotAllowedBikerException {
        Optional<Biker> biker = bikerRepository.findById(id);
        if (biker.isPresent()) {
            System.out.println(biker.get().toString());
            return biker.get();
        }
        throw new NotValidBikerException("No company with this user ID!");
    }

    private boolean checkTheUser(User user) {
        User authenticatedUser = getAuthenticatedUser();
        if (user != null && authenticatedUser != null)
            return authenticatedUser.getId() == user.getId();
        return false;
    }

    private void updateFirstNameBiker(Biker currentApplicant, Biker applicant) {
        if (applicant.getNume() != null && !currentApplicant.getNume().equals(applicant.getNume()))
            currentApplicant.setNume(applicant.getNume());
    }

    private void updateBikeparkFields(BikePark currentBikepark, BikePark bikePark){
        if (bikePark.getDenumire() != null && !currentBikepark.getDenumire().equals(bikePark.getDenumire()))
            currentBikepark.setDenumire(bikePark.getDenumire());
        if (bikePark.getDescriere() != null && !currentBikepark.getDescriere().equals(bikePark.getDescriere()))
            currentBikepark.setDescriere(bikePark.getDescriere());
        if (bikePark.getNrLocuri() != null && !currentBikepark.getNrLocuri().equals(bikePark.getNrLocuri()))
            currentBikepark.setNrLocuri(bikePark.getNrLocuri());
    }

    private void updateBikeparkContactFields(Contact currentContact, Contact contact){
        if (contact.getPhoneNumber() != null && !currentContact.getPhoneNumber().equals(contact.getPhoneNumber()))
            currentContact.setPhoneNumber(contact.getPhoneNumber());
        if (contact.getFacebookLink() != null && !currentContact.getFacebookLink().equals(contact.getFacebookLink()))
            currentContact.setFacebookLink(contact.getFacebookLink());
        if (contact.getWebsite() != null && !currentContact.getWebsite().equals(contact.getWebsite()))
            currentContact.setWebsite(contact.getWebsite());

        if (contact.getLocatie().getTara() != null && !currentContact.getLocatie().getTara().equals(contact.getLocatie().getTara()))
            currentContact.getLocatie().setTara(contact.getLocatie().getTara());
        if (contact.getLocatie().getLocalitate() != null && !currentContact.getLocatie().getLocalitate().equals(contact.getLocatie().getLocalitate()))
            currentContact.getLocatie().setLocalitate(contact.getLocatie().getLocalitate());
        if (contact.getLocatie().getProvincie() != null && !currentContact.getLocatie().getProvincie().equals(contact.getLocatie().getProvincie()))
            currentContact.getLocatie().setProvincie(contact.getLocatie().getLocalitate());
        if (contact.getLocatie().getStrada() != null && !currentContact.getLocatie().getStrada().equals(contact.getLocatie().getStrada()))
            currentContact.getLocatie().setStrada(contact.getLocatie().getStrada());
        if (contact.getLocatie().getNumber() != null && !currentContact.getLocatie().getNumber().equals(contact.getLocatie().getNumber()))
            currentContact.getLocatie().setNumber(contact.getLocatie().getNumber());
    }

    private void updateConcursFields(Concurs currentConcurs, Concurs concurs){
        if (concurs.getDenumire() != null && !currentConcurs.getDenumire().equals(concurs.getDenumire()))
            currentConcurs.setDenumire(concurs.getDenumire());
        if (concurs.getDataStart() != null && !currentConcurs.getDataStart().equals(concurs.getDataStart()))
            currentConcurs.setDataStart(concurs.getDataStart());
        if (concurs.getMinimParticipanti() != null && !currentConcurs.getMinimParticipanti().equals(concurs.getMinimParticipanti()))
            currentConcurs.setMinimParticipanti(concurs.getMinimParticipanti());
    }

    private Biker findApplicantById(Long id) {

        Optional<Biker> applicantOptional = bikerRepository.findById(id);
        /*if (!applicantOptional.isPresent()) {
            throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }*/
        Biker applicant = applicantOptional.get();

        return applicant;

    }

    @Override
    public Biker updateApplicant(Long id, Biker applicant) {

        Biker currentApplicant = findApplicantById(id);

        /*if (!checkTheUser(currentApplicant.getUser()))
            throw new NotAllowedApplicantException("You don't have permissions to update!");

        if (currentApplicant.getDescription() == null) {
            currentApplicant.setDescription("");
        }*/

        updateFirstNameBiker(currentApplicant, applicant);

        return bikerRepository.save(currentApplicant);
    }

    @Override
    public User getAuthenticatedUser() {
        //TODO MARE DE TOT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String string = (String) authentication.getPrincipal();
        //String string = "";
        User user = null;
        Optional<User> userUsername = userRepository.findByUsername(string);
        if (!userUsername.isPresent()) {
            Optional<User> userEmail = userRepository.findByEmail(string);
            if (userEmail.isPresent())
                user = userEmail.get();
        } else
            user = userUsername.get();
        return user;
    }

    //TODO CRED CA NU-L MAI FOLOSESC
    @Override
    public List<BikeparkReservationRequest> getAllRezervariBikeparkForBiker() {
        // TODO TOKEN
        User user = getAuthenticatedUser();
        //User user = new User();
        Biker biker = user.getBiker();

        Set<RezervareBikePark> allRezervariBikepark = biker.getRezervareBikeParks();
        List<RezervareBikePark> sortedList = new ArrayList<>(allRezervariBikepark);

        Collections.sort(sortedList, new Comparator<RezervareBikePark>() {
            @Override
            public int compare(RezervareBikePark o1, RezervareBikePark o2) {
                if (o1.getId().equals(o2.getId()))
                    return 0;
                return o1.getId() < o2.getId() ? -1 : 1;
            }
        });
        List<BikeparkReservationRequest> requests = new ArrayList<>();
        /*Long i = (long)0;
        for(RezervareBikePark r : sortedList){
            BikeparkReservationRequest req = new BikeparkReservationRequest(
                    i,biker,r
            );
            requests.add(req);
        }*/
        return requests;
    }

    /*@Override
    public List<RezervareBikePark> getAllRezervariBikeparkByBiker(Long id) throws NotValidBikerException {
        // TODO TOKEN
        //User user = getAuthenticatedUser();
        *//*User user = new User();
        Biker biker = user.getBiker();*//*

        Biker biker = findBikerById(id);
        //BikePark bikePark = findBikeparkById(id);

        Set<RezervareBikePark> allRezervariBikepark = biker.getRezervareBikeParks();
        List<RezervareBikePark> sortedList = new ArrayList<>(allRezervariBikepark);

        List<BikeparkReservationRequest> requests = new ArrayList<>();
        Long i = (long)1;
        for(RezervareBikePark r : sortedList){
            BikePark bikePark = r.getBikePark();
            BikeparkReservationRequest req = new BikeparkReservationRequest(
                    i,bikePark,r
            );
            requests.add(req);
            i++;
        }

        return sortedList;
    }*/

    @Override
    public List<RezervareBikePark> getAllRezervariBikeparkByBiker(Long id) throws NotValidBikerException {

        Biker biker = findBikerById(id);

        Set<RezervareBikePark> allRezervariBikepark = biker.getRezervareBikeParks();
        List<RezervareBikePark> sortedList = new ArrayList<>(allRezervariBikepark);

        /*List<BikeparkReservationRequest> requests = new ArrayList<>();
        Long i = (long)1;
        for(RezervareBikePark r : sortedList){
            BikePark bikePark = r.getBikePark();
            BikeparkReservationRequest req = new BikeparkReservationRequest(
                    i,bikePark,bikePark.getDenumire(),r
            );
            requests.add(req);
            i++;
        }*/

        return sortedList;
    }

    @Override
    public BikePark getBikeparkByIdRezervare(Long id) {

        Optional<RezervareBikePark> rezervareBikePark = rezervareBikeParkRepository.findById(id);
        RezervareBikePark rez = rezervareBikePark.get();

        BikePark bikePark = rez.getBikePark();

        return bikePark;
    }

    @Override
    public Concurs getConcursByIdInscriere(Long id) {

        Optional<RezervareConcurs> rezervareConcurs = rezervareConcursRepository.findById(id);
        RezervareConcurs rez = rezervareConcurs.get();

        Concurs concurs = rez.getConcurs();

        return concurs;
    }

    @Override
    public List<ConcursReservationRequest> getAllRezervariConcursForBiker() {
        // TODO TOKEN
        //User user = getAuthenticatedUser();
        User user = new User();
        Biker biker = user.getBiker();

        Set<RezervareConcurs> allRezervariConcurs = biker.getRezervareConcurs();
        List<RezervareConcurs> sortedList = new ArrayList<>(allRezervariConcurs);

        Collections.sort(sortedList, new Comparator<RezervareConcurs>() {
            @Override
            public int compare(RezervareConcurs o1, RezervareConcurs o2) {
                if (o1.getId().equals(o2.getId()))
                    return 0;
                return o1.getId() < o2.getId() ? -1 : 1;
            }
        });
        List<ConcursReservationRequest> requests = new ArrayList<>();
        /*Long i = (long)0;
        for(RezervareConcurs r : sortedList){
            ConcursReservationRequest req = new ConcursReservationRequest(
                    i,biker,r
            );
            requests.add(req);
        }*/
        return requests;
    }

    @Override
    public List<RezervareConcurs> getAllRezervariConcursByBiker(Long id) throws NotValidBikerException {
        // TODO TOKEN
        //User user = getAuthenticatedUser();
        /*User user = new User();
        Biker biker = user.getBiker();*/

        Biker biker = findBikerById(id);

        Set<RezervareConcurs> allRezervariConcurs = biker.getRezervareConcurs();
        List<RezervareConcurs> sortedList = new ArrayList<>(allRezervariConcurs);

        /*List<ConcursReservationRequest> requests = new ArrayList<>();
        Long i = (long)1;
        for(RezervareConcurs r : sortedList){
            Concurs concurs = r.getConcurs();
            ConcursReservationRequest req = new ConcursReservationRequest(
                    i,concurs,r
            );
            requests.add(req);
        }*/

        return sortedList;
    }

    @Override
    public RezervareBikePark rezervaBikepark(RezervareBikePark rezervareBikePark){
        if (rezervareBikeParkRepository.existsById(rezervareBikePark.getId())){
            rezervareBikePark.setId(rezervareBikePark.getId()+1);
        }
        return rezervareBikeParkRepository.save(rezervareBikePark);
    }

    @Override
    public RezervareConcurs rezervaConcurs(RezervareConcurs rezervareConcurs){
        return  rezervareConcursRepository.save(rezervareConcurs);
    }

    @Override
    public BikePark addBikepark(BikePark bikePark){
        return bikeParkRepository.save(bikePark);
    }

    @Override
    public Concurs addConcurs(Concurs concurs){
        return concursRepository.save(concurs);
    }

    @Override
    public Categorie addCategorie(Categorie categorie){
        return categorieRepository.save(categorie);
    }

    @Override
    public Traseu addTraseu(Traseu traseu){
        return traseuRepository.save(traseu);
    }

    @Override
    public Role addRole(Role role){
        return roleRepository.save(role);
    }

    @Override
    public List<Categorie> getCategoriiByConcurs(Long idConcurs) throws NotValidCategorieException {
        Optional<List<Categorie>> categoriesOptional = categorieRepository.findAllByConcurs_Id(idConcurs);
        if (!categoriesOptional.isPresent()) {
            throw new NotValidCategorieException("Concurs with ID:" + idConcurs + " doesn't have categorie!");
        }
        List<Categorie> categories = categoriesOptional.get();
        return categories;
        //return categorieRepository.findCategorieByConcurs(idConcurs);
    }

    @Override
    public BikePark getBikeparkByConcurs(Long idConcurs){
        System.out.println("Bata-l");
        Optional<BikePark> bikeParkOptional = bikeParkRepository.findByConcurs_Id(idConcurs);
        if (!bikeParkOptional.isPresent()) {
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        BikePark bikePark = bikeParkOptional.get();
        return bikePark;
    }

    @Override
    public Concurs getConcursById(Long idConcurs){
        return concursRepository.getOne(idConcurs);
    }

    @Override
    public Photo getConcursLogo(Long idConcurs) {
        Optional<BikePark> bikeParkOptional = bikeParkRepository.findByConcurs_Id(idConcurs);
        if (!bikeParkOptional.isPresent()) {
            System.out.println("Fraiere");
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        BikePark bikePark = bikeParkOptional.get();
        System.out.println(bikePark.toString());
        Optional<Contact> contactOptional = contactRepository.findByBikePark_Id(bikePark.getId());
        if (!contactOptional.isPresent()) {
            System.out.println("Fraiere");
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        Contact contact = contactOptional.get();
        System.out.println(contact.toString());
        Optional<Photo> photoOptional = photoRepository.findByContact_Id(contact.getId());
        if (!photoOptional.isPresent()) {
            System.out.println("Fraiere");
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        Photo photo = photoOptional.get();
        System.out.println(photo.toString());
        return photo;
    }

    @Override
    public Categorie deleteCategorie(Long id){
        Optional<Categorie> categorieOptional = categorieRepository.findById(id);
        if (!categorieOptional.isPresent()) {
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        Categorie categorie = categorieOptional.get();
        Optional<Concurs> concursOptional = concursRepository.findById(categorie.getConcurs().getId());
        Concurs concurs = concursOptional.get();
        concurs.removeCategorie(categorie);

        categorieRepository.delete(categorie);
        return categorie;
    }

    @Override
    public Traseu deleteTraseu(Long id){
        Optional<Traseu> traseuOptional = traseuRepository.findById(id);
        if (!traseuOptional.isPresent()) {
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        Traseu traseu = traseuOptional.get();
        Optional<BikePark> bikeParkOptional = bikeParkRepository.findById(traseu.getBikePark().getId());
        BikePark bikePark = bikeParkOptional.get();
        bikePark.removeTraseu(traseu);

        traseuRepository.delete(traseu);
        return traseu;
    }

    @Override
    public Concurs deleteConcurs(Long id){
        Optional<Concurs> concursOptional = concursRepository.findById(id);
        if (!concursOptional.isPresent()) {
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        Concurs concurs = concursOptional.get();
        Optional<BikePark> bikeParkOptional = bikeParkRepository.findById(concurs.getBikePark().getId());
        BikePark bikePark = bikeParkOptional.get();
        bikePark.removeConcurs(concurs);

        concursRepository.delete(concurs);
        return concurs;
    }

    @Override
    public RezervareBikePark deleteRezervareBikepark(Long id){
        Optional<RezervareBikePark> rezervareBikeParkOptional = rezervareBikeParkRepository.findById(id);
        if (!rezervareBikeParkOptional.isPresent()) {
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        RezervareBikePark rezervareBikePark = rezervareBikeParkOptional.get();
        Optional<Biker> bikerOptional = bikerRepository.findById(rezervareBikePark.getBiker().getId());
        Biker biker = bikerOptional.get();
        biker.removeRezervareBikepark(rezervareBikePark);

        rezervareBikeParkRepository.delete(rezervareBikePark);
        return rezervareBikePark;
    }

    @Override
    public RezervareConcurs deleteRezervareConcurs(Long id){
        Optional<RezervareConcurs> rezervareConcursOptional = rezervareConcursRepository.findById(id);
        if (!rezervareConcursOptional.isPresent()) {
            //throw new NotValidApplicantException("Applicant with ID:" + id + " doesn't exist!");
        }
        RezervareConcurs rezervareConcurs = rezervareConcursOptional.get();
        Optional<Biker> bikerOptional = bikerRepository.findById(rezervareConcurs.getBiker().getId());
        Biker biker = bikerOptional.get();
        biker.removeRezervareConcurs(rezervareConcurs);

        rezervareConcursRepository.delete(rezervareConcurs);
        return rezervareConcurs;
    }

    private Concurs findConcursById(Long id) {
        Optional<Concurs> concursOptional = concursRepository.findById(id);
        if (!concursOptional.isPresent()) {
            //throw new NotValidCompanyException("Company with ID:" + id + " doesn't exist!");
        }

        Concurs concurs = concursOptional.get();
        System.out.println(concurs.getDenumire());
        return concurs;
    }

    private Traseu findTraseuById(Long id) {
        Optional<Traseu> traseuOptional = traseuRepository.findById(id);
        if (!traseuOptional.isPresent()) {
            //throw new NotValidCompanyException("Company with ID:" + id + " doesn't exist!");
        }

        Traseu traseu = traseuOptional.get();
        return traseu;
    }

    private BikePark findBikeparkById(Long id) throws NotValidBikeparkException {
        Optional<BikePark> bikePark = bikeParkRepository.findById(id);
        if (bikePark.isPresent()) {
            return bikePark.get();
        }
        throw new NotValidBikeparkException("No company with this user ID!" + id);
        /*Optional<BikePark> bikeParkOptional = bikeParkRepository.findById(id);
        if (!bikeParkOptional.isPresent()) {
            //throw new NotValidCompanyException("Company with ID:" + id + " doesn't exist!");
        }

        BikePark bikePark1 = bikeParkOptional.get();
        System.out.println(bikePark1.getDenumire());
        return bikePark1;*/
    }

    private Contact findContactById(Long id) {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (!contactOptional.isPresent()) {
            //throw new NotValidCompanyException("Company with ID:" + id + " doesn't exist!");
        }

        Contact contact = contactOptional.get();
        return contact;
    }

    @Override
    public Biker findBikerById(Long id) throws NotValidBikerException {
        Optional<Biker> contactOptional = bikerRepository.findById(id);
        if (!contactOptional.isPresent()) {
            throw new NotValidBikerException("Biker with ID:" + id + " doesn't exist!");
        }

        Biker contact = contactOptional.get();
        return contact;
    }

    @Override
    public List<Traseu> findTraseeByBikeparkId(Long id) {
        Optional<List<Traseu>> traseuList = traseuRepository.findAllByBikePark_Id(id);

        return traseuList.get();
    }

    @Override
    public List<Concurs> findConcursuriByBikeparkId(Long id) {
        Optional<List<Concurs>> concurs = concursRepository.findAllByBikePark_Id(id);

        return concurs.get();
    }

    private RezervareConcurs findRezervareConcurstByBikerAndConcurs(Biker biker, Concurs concurs) {
        Optional<RezervareConcurs> rezervareConcursOptional = rezervareConcursRepository.findRezervareConcursByBikerAndConcurs(biker, concurs);
        if (!rezervareConcursOptional.isPresent())
            return null;
        return rezervareConcursOptional.get();
    }

    @Override
    public RezervareConcurs createRezervareConcurs(Concurs concurs, RezervareConcurs rezervareConcurs) throws NotValidBikerException, NotAllowedBikerException {

        Concurs actualConcurs = findConcursById(concurs.getId());
        //TODO BIKER
        //Biker actualBiker = findBikerById((long)20);
        User user = getAuthenticatedUser();
        System.out.println(user.getId() + " user rezervare concurs");

        Biker actualBiker = user.getBiker();
        System.out.println(actualBiker.getId() + " biker rezervare concurs");

        if (findRezervareConcurstByBikerAndConcurs(actualBiker, actualConcurs) != null)
            throw new NotAllowedBikerException("You can't apply again for this concurs!");

        actualBiker.addRezervareConcurs(rezervareConcurs);
        actualConcurs.addRezervareConcurs(rezervareConcurs);
        return rezervareConcursRepository.saveAndFlush(rezervareConcurs);


    }

    @Override
    public Categorie createCategorie(Concurs concurs, Categorie categorie) {
        Concurs actualConcurs = findConcursById(concurs.getId());

        actualConcurs.addCategorii(categorie);
        return categorieRepository.saveAndFlush(categorie);

    }

    @Override
    public Traseu createTraseu(BikePark bikePark, Traseu traseu) throws NotValidBikeparkException {
        BikePark actualBikepark = findBikeparkById(bikePark.getId());

        //Traseu actualTraseu = new Traseu();
        //traseu.setDificultate(Dificultate.usor);
        int idNou = traseuRepository.findAll().size() + 1;
        Long idTraseu = (long)idNou;
        System.out.println("Traseul cu id " + idTraseu);
        traseu.setId((long)idNou);
        /*actualTraseu.setId(idTraseu);
        actualTraseu.setDificultate(traseu.getDificultate());
        actualTraseu.setTipTraseu(traseu.getTipTraseu());
        actualTraseu.setLungime(traseu.getLungime());
        actualTraseu.setDenumire(traseu.getDenumire());*/

        actualBikepark.addTraseu(traseu);
        //TODO nu salva in repository
        //return traseuRepository.saveAndFlush(traseu);
        return traseu;

    }

    @Override
    public Concurs createConcurs(BikePark bikePark, Concurs concurs) throws NotValidBikeparkException {
        BikePark actualBikepark = findBikeparkById(bikePark.getId());

        actualBikepark.addConcurs(concurs);
        return concursRepository.saveAndFlush(concurs);

    }

    @Override
    public RezervareBikePark createRezervareBikepark(BikePark bikePark, RezervareBikePark rezervareBikePark) throws NotValidBikeparkException, NotValidBikerException {

        BikePark actualBikepark = getBikeparkById(bikePark.getId());

        User user = getAuthenticatedUser();
        System.out.println(user.getId() + " user rezervare bikepark");

        Biker actualBiker = user.getBiker();
        System.out.println(actualBiker.getId() + " biker rezervare bikepark");

        //Biker actualBiker = findBikerById((long)20);
        //Rezervarea deja e construita; mai trebuie legata de bikepark si de biker

        actualBiker.addRezervareBikeParks(rezervareBikePark);
        actualBikepark.addRezervareBikeParks(rezervareBikePark);
        RezervareBikePark rezervareBikeParkFinal = rezervareBikeParkRepository.saveAndFlush(rezervareBikePark);

        modificaPreferinte(actualBiker, actualBikepark);

        //TODO CALCULARE PREFERINTA PT BIKER
        //TODO SE FACE DUPA NR DE REZERVARI

        return rezervareBikeParkFinal;
    }

    @Override
    public Biker registerBiker(User user, Biker biker) {
        user.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleStringEquals(RoleString.BIKER));
        user.setRoles(roles);
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userResult = userRepository.save(user);
        userResult.setBiker(biker);
        Biker currentBiker = bikerRepository.saveAndFlush(biker);
        Contact contact = currentBiker.getContact();
        if (contact == null) {
            initDefaultContactForBiker(currentBiker);

            Contact currentContact = currentBiker.getContact();

            initDefaultAddressForBiker(currentContact);
            initDefaulPhotoForBiker(currentContact);
        } else {
            //setLatLngForAddress(contact.getAddress());
        }
        if (biker.getAniExperienta() == null) {
            biker.setAniExperienta(0);
        }
        return currentBiker;
    }

    @Override
    public BikePark registerBikepark(User user, BikePark bikePark) {
        user.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleStringEquals(RoleString.BIKEPARK));
        user.setRoles(roles);
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userResult = userRepository.save(user);
        userResult.setBikePark(bikePark);
        BikePark currentBikepark = bikeParkRepository.saveAndFlush(bikePark);
        Contact contact = currentBikepark.getContact();

        if (contact != null) {
            //setLatLngForAddress(contact.getAddress());
        } else {
            initDefaultContactForBikepark(currentBikepark);
            initDefaultAddressForBikepark(currentBikepark.getContact());
            initDefaulPhotoForBikepark(currentBikepark);

        }

        return currentBikepark;
    }

    @Override
    public boolean checkUsernameExists(User user) {
        return userRepository.findByUsername(user.getUsername()).isPresent();
    }


    @Override
    public boolean checkEmailExists(User user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }

    public void modificaPreferinte(Biker biker, BikePark bikePark){
        List<Prefera> preferaList = new ArrayList<>();
        preferaList = loadPreferinte();
        System.out.println("Modifica Preferinte inceput " + preferaList.size());

        //TODO GET USER BIKER
        //Biker actualBiker = findBikerById((long)1);

        int gasit = 0;
        for(Prefera p : preferaList){
            if(p.getUser_id().equals(biker.getId()) && p.getItem_id().equals(bikePark.getId())){
                gasit = 1;
                if(p.getPreference() <= 3.5){
                    p.setPreference(p.getPreference() + 0.5);
                }
            }
        }
        System.out.println("Ce a gasit " + gasit);
        if (gasit == 0){
            Prefera prefera = new Prefera(biker.getId(),bikePark.getId(),0.5);
            preferaList.add(prefera);
        }

        System.out.println("Modifica Preferinte final " + preferaList.size());
        modificaFisier(preferaList);
    }

    public void modificaFisier(List<Prefera> preferaList){

        String filename = "C:\\Users\\Gabi\\IdeaProjects\\ProiectBikePark_Final\\src\\main\\resources\\prefera.txt";
        try(PrintWriter pw = new PrintWriter(filename)){
            for(Prefera p : preferaList){
                String line = ""+p.getUser_id()+ ","+
                        p.getItem_id()+ ","+
                        (double)p.getPreference()+ "\n";
                pw.write(line);
            }
        }catch(IOException e){
            System.err.println(e);
        }
    }

    public List<BikePark> getBikeparkRecomandate(List<RecommendedItem> recommendedItems){
        List<Long> idBikepark = new ArrayList<>();
        List<BikePark> bikeParks = new ArrayList<>();
        for(RecommendedItem item : recommendedItems){
            idBikepark.add(item.getItemID());
        }

        for(Long id : idBikepark){
            try {
                bikeParks.add(getBikeparkById(id));
            } catch (NotValidBikeparkException e) {
                e.printStackTrace();
            }
        }

        return bikeParks;
    }

    public List<RecommendedItem> recomandaLista(Long idUser){
        List<RecommendedItem> itemRecommendations = null;
        try {
            DataModel dataModel = new FileDataModel(new File("C:\\Users\\Gabi\\IdeaProjects\\ProiectBikePark_Final\\src\\main\\resources\\prefera.txt"));

            ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity(dataModel);
            Recommender itemRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
            itemRecommendations = itemRecommender.recommend(idUser, DEFAULT_LIMIT);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return itemRecommendations;
    }

    public List<Prefera> loadPreferinte(){
        List<Prefera> preferaList = new ArrayList<>();
        try {
            String filename = "C:\\Users\\Gabi\\IdeaProjects\\ProiectBikePark_Final\\src\\main\\resources\\prefera.txt";

            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Prefera elev = new Prefera(Long.parseLong(values[0]), Long.parseLong(values[1]), Double.parseDouble(values[2]));
                preferaList.add(elev);
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Cate preferinte sunt " + preferaList.size());
        return preferaList;
    }

        //TODO 2071 Itemi
        //TODO 1508 Useri
        //TODO 35497 Preferinte totale

    public List<BikePark> recomanda(Biker biker) {
        List<BikePark> bikeParks = new ArrayList<>();
        bikeParks = getBikeparkRecomandate(recomandaLista(biker.getId()));
        System.out.println("Lungime bikepark recomandate " + bikeParks.size());

        return bikeParks;
    }

    @Override
    public BikePark updateBikepark(Long id, BikePark bikePark) throws NotValidBikeparkException {

        BikePark currentBikepark = findBikeparkById(id);

        updateBikeparkFields(currentBikepark, bikePark);

        return bikeParkRepository.save(currentBikepark);
    }

    @Override
    public Contact updateBikeparkContact(Long id, Contact contact) {

        Contact currentContact = findContactById(id);

        updateBikeparkContactFields(currentContact, contact);

        return contactRepository.save(currentContact);
    }

    @Override
    public Concurs updateConcurs(Long id, Concurs concurs) {

        Concurs currentConcurs = findConcursById(id);

        updateConcursFields(currentConcurs, concurs);

        return concursRepository.save(currentConcurs);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<BikePark> recommend(Biker user, int howMany) {
        BikePark bikePark = bikeParkRepository.getOne((long)1);

        //TODO SUNT PROST, ACUM VAD CA E TRANSACTIONAL

        System.out.println(user.getPrenume());
        System.out.println(howMany);
        if (howMany <= 0) {
            howMany = DEFAULT_LIMIT;
        }
        System.out.println(howMany);
        List<BikePark> recomandate = getRecommendedMovies(getItems(user.getId(), howMany));

        return recomandate;
        //return getRecommendedMovies(getItems(user.getId(), howMany));
    }

    private List<BikePark> getRecommendedMovies(final List<RecommendedItem> items) {
        final List<BikePark> movies = new ArrayList<>();
        int count = 0;
        System.out.println("Face recomandare");
        for (final RecommendedItem item : items) {
            System.out.println("Intra la recomandari");
            System.out.println(item.getItemID() + " " + item.getValue());
            Optional<BikePark> movieOptional = bikeParkRepository.findById(item.getItemID());
            BikePark movie = movieOptional.get();
            //movie.setRank(++count);
            movies.add(movie);
            //recommender.mostSimilarItems(1,2);
        }

        return movies;
    }

    private List<RecommendedItem> getItems(final Long userId, final int howMany) {
        List<RecommendedItem> items = null;
        try {
            items = recommender.recommend(userId, howMany);
            //items = null; //recommender.recommend(userId, howMany);
        } catch (Exception e) { //TasteException
            throw new RecommendationException("Unable to make recommendation for userId: " + userId);
        }
        //calculateSimilarities();

        return items;
    }

    public Preferinte findByBikerAndBikepark(Biker biker,BikePark bikePark){
        List<Preferinte> preferinteList = preferintaRepository.findAll();
        Preferinte preferinte = null;
        for(Preferinte p : preferinteList){
            if(p.getUser_id().equals(biker) && p.getItem_id().equals(bikePark))
                preferinte = p;
        }
        return preferinte;
    }
}
