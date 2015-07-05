package br.edu.ufba.softvis.visminer.model.database;

import java.io.Serializable;

import javax.persistence.*;

import br.edu.ufba.softvis.visminer.constant.SoftwareUnitType;

import java.util.List;


/**
 * @author Felipe Gustavo de Souza Gomes (felipegustavo1000@gmail.com)
 * @version 0.9
 * The persistent class for the software_unit database table.
 */
@Entity
@Table(name="software_unit")
@NamedQueries({
	@NamedQuery(name="SoftwareUnitDB.findByUid", query="select sw from SoftwareUnitDB sw where sw.uid = :uid"),
	@NamedQuery(name="SoftwareUnitDB.findByRepository", query="select sw from SoftwareUnitDB sw where sw.repository.id = :id")
})
public class SoftwareUnitDB implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOFTWARE_UNIT_ID_GENERATOR", sequenceName="SOFTWARE_UNIT_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SOFTWARE_UNIT_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=256)
	private String name;

	@Column(nullable=false)
	private int type;

	@Column(nullable=false, length=40)
	private String uid;

	//bi-directional many-to-one association to FileDB
	@ManyToOne
	@JoinColumn(name="file_id")
	private FileDB file;

	//bi-directional many-to-one association to RepositoryDB
	@ManyToOne
	@JoinColumn(name="repository_id", nullable=false)
	private RepositoryDB repository;

	//bi-directional many-to-one association to SoftwareUnitDB
	@ManyToOne
	@JoinColumn(name="parent_id", nullable=true)
	private SoftwareUnitDB softwareUnit;

	//bi-directional many-to-one association to SoftwareUnitDB
	@OneToMany(mappedBy="softwareUnit")
	private List<SoftwareUnitDB> softwareUnits;

	//bi-directional many-to-many association to CommitDB
	@ManyToMany(mappedBy="softwareUnits")
	private List<CommitDB> commits;

	public SoftwareUnitDB() {
	}

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param uid
	 */
	public SoftwareUnitDB(int id, String name, SoftwareUnitType type,
			String uid) {
		super();
		this.id = id;
		this.name = name;
		this.type = type.getId();
		this.uid = uid;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public SoftwareUnitType getType() {
		return SoftwareUnitType.parse(type);
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SoftwareUnitType type) {
		this.type = type.getId();
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the file
	 */
	public FileDB getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(FileDB file) {
		this.file = file;
	}

	/**
	 * @return the repository
	 */
	public RepositoryDB getRepository() {
		return repository;
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(RepositoryDB repository) {
		this.repository = repository;
	}

	/**
	 * @return the softwareUnit
	 */
	public SoftwareUnitDB getSoftwareUnit() {
		return softwareUnit;
	}

	/**
	 * @param softwareUnit the softwareUnit to set
	 */
	public void setSoftwareUnit(SoftwareUnitDB softwareUnit) {
		this.softwareUnit = softwareUnit;
	}

	/**
	 * @return the softwareUnits
	 */
	public List<SoftwareUnitDB> getSoftwareUnits() {
		return softwareUnits;
	}

	/**
	 * @param softwareUnits the softwareUnits to set
	 */
	public void setSoftwareUnits(List<SoftwareUnitDB> softwareUnits) {
		this.softwareUnits = softwareUnits;
	}

	/**
	 * @return the commits
	 */
	public List<CommitDB> getCommits() {
		return commits;
	}

	/**
	 * @param commits the commits to set
	 */
	public void setCommits(List<CommitDB> commits) {
		this.commits = commits;
	}

}