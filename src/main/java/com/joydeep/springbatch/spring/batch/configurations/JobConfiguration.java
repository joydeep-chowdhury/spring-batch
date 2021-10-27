package com.joydeep.springbatch.spring.batch.configurations;

import com.joydeep.springbatch.spring.batch.dtos.EmployeeDTO;
import com.joydeep.springbatch.spring.batch.mappers.EmployeeFileRowMapper;
import com.joydeep.springbatch.spring.batch.models.Employee;
import com.joydeep.springbatch.spring.batch.services.processors.EmployeeProcessor;
import com.joydeep.springbatch.spring.batch.services.writers.EmployeeDBWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EmployeeProcessor employeeProcessor;
    private final JobRepository jobRepository;
    private final EmployeeDBWriter employeeDBWriter;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource,
                            JobRepository jobRepository, EmployeeDBWriter employeeDBWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.jobRepository = jobRepository;
        this.employeeDBWriter = employeeDBWriter;
    }

    @Qualifier(value = "testjob")
    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("testjob")
                                .start(firstStep())
                                .build();
    }

    @Bean(name = "firststep")
    public Step firstStep() throws Exception {
        return stepBuilderFactory.get("firststep")
                                 .<EmployeeDTO, Employee> chunk(5)
                                 .reader(employeeReader())
                                 .processor(employeeProcessor)
                                 .writer(employeeDBWriter)
                                 .build();
    }

    @Bean
    @StepScope
    public Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) throws Exception {
        return new ClassPathResource(fileName);
    }

    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDTO> employeeReader() throws Exception {
        FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
        reader.setResource(inputFileResource(null));
        reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("employeeId", "firstName", "lastName", "email", "age");
                        setDelimiter(",");
                    }
                });
                setFieldSetMapper(new EmployeeFileRowMapper());
            }
        });
        return reader;
    }

    @Bean
    public JobLauncher simpleJobLauncher() {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }
}
